import { createContext, useContext, useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";


export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const apiClient = axios.create({
    baseURL: backendUrl,
    withCredentials: true,
  });

  useEffect(() => {
    const checkAuth = async () => {
      try {
        await apiClient.get("/public/check-auth");
        setIsAuthenticated(true);
      } catch {
        setIsAuthenticated(false);
      }
    };
    checkAuth();
  }, []);

  let isRefreshing = false;

  // Response interceptor for token refresh
  apiClient.interceptors.response.use(
    (res) => res,
    async (err) => {
      const originalRequest = err.config;

      if (err.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        if (!isRefreshing) {
          isRefreshing = true;
          try {
            await axios.get(`${backendUrl}/public/refresh-token`, {
              withCredentials: true,
            });
            isRefreshing = false;
            return apiClient(originalRequest);
          } catch (refreshError) {
            isRefreshing = false;
            navigate("/login"); // Navigate on refresh failure
            return Promise.reject(refreshError);
          }
        } else {
          return new Promise((resolve) => {
            const checkRefresh = setInterval(() => {
              if (!isRefreshing) {
                clearInterval(checkRefresh);
                resolve(apiClient(originalRequest));
              }
            }, 100);
          });
        }
      }

      return Promise.reject(err);
    }
  );

  // Logout function
  const logout = async () => {
    try {
      await apiClient.post(`${backendUrl}/user/logout`);
      setIsAuthenticated(false);
      toast.success('Logout Successful');
      navigate("/login");
    } catch (error) {
      console.error("Logout failed:", error);
      setIsAuthenticated(false);
      navigate("/login");
    }
  };

  // All Subjects
  const AllSubjectsData = async () => {
    try {
      const response = await axios.get(`${backendUrl}/public/get-subject-structure`);
      return response.data;
    } catch (error) {
      console.error("Error fetching subjects:", error);
      return [];
    }
  };
  



  const value = {
    backendUrl,
    apiClient,
    logout, 
    isAuthenticated,
    AllSubjectsData,
  };

  return (
    <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = () => useContext(AppContext);