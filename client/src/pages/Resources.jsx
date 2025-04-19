import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import Select from "react-select";
import { useAppContext } from "../context/AppContext.jsx";
import { toast } from "react-toastify";
import NoteCard from "../components/NoteCard.jsx";
import { useAllContext } from "../context/AllContext.jsx";
import { Circles } from "react-loader-spinner";

const Resources = () => {
  const [courses, setCourses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [sortBy, setSortBy] = useState({ label: "ID", value: "id" });
  const [sortingOrder, setSortingOrder] = useState({
    label: "Ascending",
    value: "ASC",
  });
  const [pageNumber, setPageNumber] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const location = useLocation();
  const resourceType = location.pathname.split("/").pop();

  const { backendUrl, apiClient, isAuthenticated, AllSubjectsData } =
    useAppContext();
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

  const handleSearch = async () => {
    if (!selectedCourse) {
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

    params.append("pageNumber", pageNumber);
    params.append("pageSize", 8);
    params.append("sortBy", sortBy.value);
    params.append("sortingOrder", sortingOrder.value);

    try {
      setLoading(true);
      setNotes([]);

      const res = await apiClient.get(
        `${backendUrl}/${resourceType}/all?${params.toString()}`
      );

      const fetchedNotes = Array.isArray(res.data.content)
        ? res.data.content
        : [];
      setNotes(fetchedNotes);
      setTotalPages(res.data.totalPages);
    } catch (error) {
      console.error("Failed to fetch notes:", error.message);

      if (error.response && error.response.status === 404) {
        toast.warning("No results found for the selected filters.");
      } else {
        toast.error(error.message || "Failed to fetch notes");
      }
    } finally {
      setLoading(false);
    }
  };

  // Call handleSearch when page number changes
  useEffect(() => {
    handleSearch();
  }, [
    pageNumber,
    selectedCourse,
    selectedDepartment,
    selectedSubject,
    sortBy,
    sortingOrder,
  ]);

  return (
    isAuthenticated && (
      <div className="w-full min-h-screen flex">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            setPageNumber(0);
            handleSearch();
          }}
          className="w-[25%] bg-[#343E4F] flex flex-col space-y-5 pt-10 px-2"
        >
          {/* Filters and Dropdowns */}
          <fieldset className="border border-white p-2">
            <legend className="text-white font-semibold">Course</legend>
            <Select
              options={courses}
              value={selectedCourse}
              onChange={setSelectedCourse}
              placeholder="Select Course"
              className="text-black"
              required
            />
          </fieldset>
          <fieldset className="border border-white p-2">
            <legend className="text-white font-semibold">Department</legend>
            <Select
              options={departments}
              value={selectedDepartment}
              onChange={setSelectedDepartment}
              placeholder="Select Department"
              className="text-black"
              isClearable
            />
          </fieldset>

          <fieldset className="border border-white p-2 rounded-md">
            <legend className="text-white font-semibold">Subject</legend>
            <Select
              options={subjects}
              value={selectedSubject}
              onChange={setSelectedSubject}
              placeholder="Select Subject"
              className="text-black"
              isClearable
            />
          </fieldset>
          <fieldset className="border border-white p-2">
            <legend className="text-white font-semibold">Sorting By</legend>
            <Select
              options={[
                { label: "ID", value: "id" },
                { label: "Topic Name", value: "topicName" },
                { label: "Upload Date", value: "uploadDateTime" },
              ]}
              value={sortBy}
              onChange={setSortBy}
              placeholder="Sort By"
              className="text-black"
            />
          </fieldset>

          <fieldset className="border border-white p-2">
            <legend className="text-white font-semibold">Sorting Order</legend>
            <Select
              options={[
                { label: "Ascending", value: "ASC" },
                { label: "Descending", value: "DESC" },
              ]}
              value={sortingOrder}
              onChange={setSortingOrder}
              placeholder="Sort Order"
              className="text-black"
            />
          </fieldset>

          {/* <button className="w-full p-2 bg-white text-btngreen rounded-full text-xl font-semibold hover:bg-btngreen hover:text-whitee">
            Search
          </button> */}
        </form>

        {/* Notes Display */}
        <div className="bg-darkbg w-full flex flex-col p-6 overflow-y-auto">
          {loading ? (
            <div className="flex items-center justify-center w-full h-full">
              <Circles
                height="80"
                width="80"
                color="#00BFFF"
                ariaLabel="loading"
              />
            </div>
          ) : notes.length > 0 ? (
            <>
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

              {/* Pagination Controls */}
              <div className="flex gap-x-4 justify-center items-center pt-10">
                <button
                  onClick={() => setPageNumber((prev) => Math.max(prev - 1, 0))}
                  disabled={pageNumber === 0}
                  className="bg-btngreen px-4 py-2 rounded text-white disabled:opacity-50"
                >
                  Previous
                </button>

                <span className="text-white font-semibold">
                  Page {pageNumber + 1}
                </span>

                <button
                  onClick={() =>
                    setPageNumber((prev) => Math.min(prev + 1, totalPages - 1))
                  }
                  disabled={pageNumber === totalPages - 1}
                  className="bg-btngreen px-4 py-2 rounded text-white disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </>
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
