import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Select from "react-select";
import { useAppContext } from "../context/AppContext.jsx";
import { toast } from "react-toastify";
import NoteCard from "../components/NoteCard.jsx";
import { useAllContext } from "../context/AllContext.jsx";
import { Circles } from "react-loader-spinner";

const Resources = () => {
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
  const navigate = useNavigate();
  const resourceType = location.pathname.split("/").pop();

  const {
    backendUrl,
    apiClient,
    isAuthenticated,
    courses,
    departments,
    subjects,
  } = useAppContext();
  const { formatDate } = useAllContext();

  // Update URL based on current selections
  const updateURL = ({
    course = selectedCourse,
    department = selectedDepartment,
    subject = selectedSubject,
    sort = sortBy,
    order = sortingOrder,
    page = pageNumber,
  }) => {
    const params = new URLSearchParams();
    if (course) params.set("course", course.label);
    if (department) params.set("department", department.label);
    if (subject) params.set("subject", subject.label);
    if (sort) params.set("sortBy", sort.value);
    if (order) params.set("sortOrder", order.value);
    if (page !== undefined) params.set("pageNumber", page + 1);

    const newUrl = `${location.pathname}?${params.toString()}`;
    navigate(newUrl, { replace: true });
  };

  useEffect(() => {
    if (!selectedCourse) return;

    const fetchNotes = async () => {
      const params = new URLSearchParams();
      params.append("courseName", selectedCourse.label);
      if (selectedDepartment) params.append("departmentName", selectedDepartment.label);
      if (selectedSubject) params.append("subjectName", selectedSubject.label);
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
        console.error("Fetch error:", error.message);

        if (error.response?.status === 404) {
          toast.warning("No data found with the above filters");
        } else {
          toast.error(error.message || "Something went wrong");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchNotes();
  }, [
    pageNumber,
    selectedCourse,
    selectedDepartment,
    selectedSubject,
    sortBy,
    sortingOrder,
  ]);

  // Dynamic sorting options based on resourceType
  const sortingOptions =
    resourceType === "notes"
      ? [
          { label: "ID", value: "id" },
          { label: "Topic Name", value: "topicName" },
          { label: "Upload Date", value: "uploadDateTime" },
        ]
      : [
          { label: "ID", value: "id" },
          { label: "Year", value: "year" },
          { label: "Upload Date", value: "uploadDateTime" },
        ];

  return (
    isAuthenticated && (
      <div className="w-full min-h-screen flex">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            setPageNumber(0);
          }}
          className="w-[25%] bg-[#343E4F] flex flex-col space-y-5 pt-10 px-2"
        >
          <fieldset className="border border-white p-2 rounded-md">
            <legend className="text-white font-semibold">Course</legend>
            <Select
              options={courses}
              value={selectedCourse}
              onChange={(val) => {
                setSelectedCourse(val);
                updateURL({ course: val });
              }}
              placeholder="Select Course"
              className="text-black"
              required
            />
          </fieldset>

          <fieldset className="border border-white p-2 rounded-md">
            <legend className="text-white font-semibold">Department</legend>
            <Select
              options={departments}
              value={selectedDepartment}
              onChange={(val) => {
                setSelectedDepartment(val);
                updateURL({ department: val });
              }}
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
              onChange={(val) => {
                setSelectedSubject(val);
                updateURL({ subject: val });
              }}
              placeholder="Select Subject"
              className="text-black"
              isClearable
            />
          </fieldset>

          <fieldset className="border border-white p-2 rounded-md">
            <legend className="text-white font-semibold">Sorting By</legend>
            <Select
              options={sortingOptions}
              value={sortBy}
              onChange={(val) => {
                setSortBy(val);
                updateURL({ sort: val });
              }}
              placeholder="Sort By"
              className="text-black"
            />
          </fieldset>

          <fieldset className="border border-white p-2 rounded-md">
            <legend className="text-white font-semibold">Sorting Order</legend>
            <Select
              options={[
                { label: "Ascending", value: "ASC" },
                { label: "Descending", value: "DESC" },
              ]}
              value={sortingOrder}
              onChange={(val) => {
                setSortingOrder(val);
                updateURL({ order: val });
              }}
              placeholder="Sort Order"
              className="text-black"
            />
          </fieldset>
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
                    userImage={note.imageUrl}
                    uploadDate={formatDate(note.uploadDateTime)}
                    viewLink={`${backendUrl}/${resourceType}/get/${note.id}?option=view`}
                    downloadLink={`${backendUrl}/${resourceType}/get/${note.id}?option=download`}
                  />
                ))}
              </div>

              <div className="flex gap-x-4 justify-center items-center pt-10">
                <button
                  onClick={() => {
                    const newPage = Math.max(pageNumber - 1, 0);
                    setPageNumber(newPage);
                    updateURL({ page: newPage });
                  }}
                  disabled={pageNumber === 0}
                  className="bg-btngreen px-4 py-2 rounded text-white disabled:opacity-50"
                >
                  Previous
                </button>

                <span className="text-white font-semibold">
                  Page {pageNumber + 1}
                </span>

                <button
                  onClick={() => {
                    const newPage = Math.min(pageNumber + 1, totalPages - 1);
                    setPageNumber(newPage);
                    updateURL({ page: newPage });
                  }}
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
                <br />
                in quick and simple words
              </p>
            </div>
          )}
        </div>
      </div>
    )
  );
};

export default Resources;
