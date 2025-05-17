import { createContext, useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userInfo, setUserInfo] = useState(null);
  const [courses, setCourses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [userUploads, setUserUploads] = useState(null);
  const [bookmarked, setBookmarked] = useState(null);

  const apiClient = axios.create({
    baseURL: backendUrl,
    withCredentials: true,
  });

  useEffect(() => {
    const checkAuth = async () => {
      try {
        await apiClient.get("/user/check-auth");
        setIsAuthenticated(true);
        await userData();
      } catch {
        setIsAuthenticated(false);
      }
    };
    checkAuth();
  }, []);

  // Login function
  const login = async () => {
    try {
      await apiClient.get("/user/check-auth");
      setIsAuthenticated(true);
      await userData();
    } catch (error) {
      setIsAuthenticated(false);
      console.error("Login check failed:", error);
    }
  };

  // Logout function
  const logout = async () => {
    try {
      await apiClient.post(`${backendUrl}/user/logout`);
      setIsAuthenticated(false);
      toast.success("Logout Successful");
      navigate("/login");
    } catch (error) {
      console.error("Logout failed:", error);
      setIsAuthenticated(false);
      navigate("/login");
    }
  };

  // Get User Profile details
  const userData = async () => {
    try {
      const res = await apiClient.get(`${backendUrl}/user/get-user-details`);
      setUserInfo(res.data);
    } catch (error) {
      console.error("Error fetching user details:", error);
    }
  };

  // Fetch all subjects, courses, and departments
  const fetchSubjects = async () => {
    try {
      const response = await axios.get(
        `${backendUrl}/public/get-subject-structure`
      );
      const subjectData = response.data;

      const uniqueCourses = [
        ...new Set(subjectData.map((item) => item.courseName)),
      ].map((course) => ({ label: course, value: course }));
      setCourses(uniqueCourses);

      const uniqueDepartments = [
        ...new Set(subjectData.map((item) => item.departmentName)),
      ].map((dept) => ({ label: dept, value: dept }));
      setDepartments(uniqueDepartments);

      const uniqueSubjects = [
        ...new Set(subjectData.map((item) => item.subjectName)),
      ].map((subjectName) => {
        const sub = subjectData.find(
          (item) => item.subjectName === subjectName
        );
        return { label: subjectName, value: sub.subjectCode };
      });
      setSubjects(uniqueSubjects);
    } catch (error) {
      console.error("Error fetching subjects:", error);
    }
  };

  // Get User Uploaded Docs
  const userUploadedDocs = async () => {
    try {
      const response = await apiClient.get(`${backendUrl}/user/get-uploaded-docs`);
      setUserUploads(response.data);
    } catch (error) {
      console.error("Error fetching userUploadedDocs details:", error);
    }
  };

  // Get User Uploaded Docs
  const userBookmarkedDocs = async () => {
    try {
      const res = await apiClient.get(`${backendUrl}/user/get-bookmarked-docs`);
      setBookmarked(res.data);
    } catch (error) {
      console.error("Error fetching BookmarkedDocs details:", error);
    }
  };

  useEffect(() => {
    fetchSubjects(),
    userUploadedDocs(),
    userBookmarkedDocs()
  }, []);

  const value = {
    backendUrl,
    apiClient,
    login,
    logout,
    isAuthenticated,
    userData,
    userInfo,
    courses,
    departments,
    subjects,
    userUploads,
    bookmarked,
    userBookmarkedDocs,
  };

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
};

export const useAppContext = () => useContext(AppContext);
