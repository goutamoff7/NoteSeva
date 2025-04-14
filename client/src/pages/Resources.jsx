import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import Select from "react-select";
import { useAppContext } from "../context/AppContext.jsx";
import { toast } from "react-toastify";
import NoteCard from "../components/NoteCard.jsx";
import { useAllContext } from "../context/AllContext.jsx";

const Resources = () => {
  const [courses, setCourses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [subjects, setSubjects] = useState([]);

  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [notes, setNotes] = useState([]);

  const location = useLocation();
  const resourceType = location.pathname.split("/").pop();

  const { backendUrl, apiClient, isAuthenticated, AllSubjectsData } = useAppContext();
  const { formatDate } = useAllContext();

  useEffect(() => {
    const fetchSubjects = async () => {
      const subjectData = await AllSubjectsData();

      const uniqueCourses = [
        ...new Set(subjectData.map((item) => item.courseName)),
      ].map((course) => ({
        label: course,
        value: course,
      }));
      setCourses(uniqueCourses);

      const uniqueDepartments = [
        ...new Set(subjectData.map((item) => item.departmentName)),
      ].map((dept) => ({
        label: dept,
        value: dept,
      }));
      setDepartments(uniqueDepartments);

      const uniqueSubjects = [
        ...new Set(subjectData.map((item) => item.subjectName)),
      ].map((subjectName) => {
        const sub = subjectData.find(
          (item) => item.subjectName === subjectName
        );
        return {
          label: subjectName,
          value: sub.subjectCode,
        };
      });
      setSubjects(uniqueSubjects);
    };

    fetchSubjects();
  }, []);

  const handleSearch = async (e) => {
    e.preventDefault();

    if (!selectedCourse) {
      toast.warning("Please select a course");
      return;
    }

    const params = new URLSearchParams();
    params.append("courseName", selectedCourse.label);

    if (selectedDepartment) {
      params.append("departmentName", selectedDepartment.label);
    }

    if (selectedSubject) {
      params.append("subjectName", selectedSubject.label);
    }

    try {
      const res = await apiClient.get(
        `${backendUrl}/${resourceType}/all?${params.toString()}`
      );
      console.log("Fetched Notes:", res.data);

      const fetchedNotes = Array.isArray(res.data.content)
        ? res.data.content
        : [];

      if (fetchedNotes.length === 0) {
        toast.info("No notes found for selected filters");
      }

      setNotes(fetchedNotes);
    } catch (error) {
      console.error("Failed to fetch notes:", error.message);
      toast.error("Failed to fetch notes");
    }
  };

  return (
    isAuthenticated && (
      <div className="w-full min-h-screen flex">
        <form
          onSubmit={handleSearch}
          className="w-[25%] bg-[#343E4F] flex flex-col space-y-5 pt-10 px-2"
        >
          {/* Courses dropdown */}
          <div>
            <Select
              options={courses}
              value={selectedCourse}
              onChange={setSelectedCourse}
              placeholder="Select Course"
              className="text-black"
              required
            />
          </div>

          {/* Department dropdown */}
          <div>
            <Select
              options={departments}
              value={selectedDepartment}
              onChange={setSelectedDepartment}
              placeholder="Select Department"
              className="text-black"
            />
          </div>

          {/* Subject dropdown */}
          <div>
            <Select
              options={subjects}
              value={selectedSubject}
              onChange={setSelectedSubject}
              placeholder="Select Subject"
              className="text-black"
            />
          </div>

          <button className="w-full p-2 bg-white text-btngreen rounded-full text-xl font-semibold hover:bg-btngreen hover:text-whitee">
            Search
          </button>
        </form>

        {/* Right Part */}
        <div className="bg-darkbg w-full flex p-6 overflow-y-auto">
          {notes.length > 0 ? (
            <div className="flex flex-wrap gap-6 h-fit">
              {notes.map((note, index) => (
                <NoteCard
                  key={note.id || index}
                  id={note.id || index}
                  title={note.topicName}
                  year={note.year}
                  subject={note.subjectName}
                  userName={note.sharedBy}
                  noteImage={note.noteImage}
                  userImage={note.userImage}
                  uploadDate={formatDate(note.uploadDateTime)}
                  downloadLink={`${backendUrl}/${resourceType}/download/${note.id}`}
                />
              
              ))}
            </div>
          ) : (
            <div className="flex flex-col items-center justify-center h-full space-y-5">
              <img
                src="/amico.png"
                alt="Search Illustration"
                className="w-[450px]"
              />
              <p className="text-white font-semibold text-3xl text-center">
                <span className="text-btngreen">Search</span> Your Requirement{" "}
                <br /> in quick and simple words
              </p>
            </div>
          )}
        </div>
      </div>
    )
  );
};

export default Resources;
