import React, { useState, useEffect, useRef } from "react";
import Select from "react-select";
import { useAppContext } from "../context/AppContext.jsx";
import { toast } from "react-toastify";

const PYQUpload = () => {
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const [year, setYear] = useState("");
  const [file, setFile] = useState(null);

  const fileInputRef = useRef(null);

  const {apiClient, backendUrl, isAuthenticated, courses, departments, subjects,} = useAppContext();

  // Handle Form Submit
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !selectedCourse ||
      !selectedDepartment ||
      !selectedSubject ||
      !year ||
      !file
    ) {
      toast.warning("Please fill all the required fields");
      return;
    }

    const formData = new FormData();

    // Append file
    formData.append("file", file);

    const pyqDTO = {
      year,
      subjectName: selectedSubject.label,
      departmentName: selectedDepartment.label,
      courseName: selectedCourse.label,
    };

    formData.append(
      "pyqDTO",
      new Blob([JSON.stringify(pyqDTO)], {
        type: "application/json",
      })
    );

    try {
      const res = await apiClient.post(`${backendUrl}/pyq/upload`, formData);
      toast.success("PYQ uploaded successfully!");
      // Reset form after success
      setSelectedCourse(null);
      setSelectedDepartment(null);
      setSelectedSubject(null);
      setYear("");
      setFile(null);
      fileInputRef.current.value = null;
    } catch (err) {
      console.error("Upload failed:", err.message);
      toast.error(err.message || "Upload failed");
    }
  };

  return (
    isAuthenticated && (
      <div className="min-h-screen flex items-center justify-evenly bg-darkbg text-white">
        <div className="max-w-[400px] space-y-[30px]">
          <img src="/notesupload.png" alt="" className="h-[300px] w-[380px]" />
          <h2 className="font-bold text-white text-2xl leading-[40px] text-center">
            <span className="text-btngreen">PYQs</span> — because smart prep
            beats hard prep
          </h2>
        </div>

        <form
          onSubmit={handleSubmit}
          className="w-full max-w-md space-y-[20px]"
        >
          <h2 className="text-2xl font-semibold mb-6 text-white">PYQ Upload</h2>

          {/* Course Dropdown */}
          <Select
            options={courses}
            value={selectedCourse}
            onChange={setSelectedCourse}
            placeholder="Select Course"
            className="text-black"
          />

          {/* Department Dropdown */}
          <Select
            options={departments}
            value={selectedDepartment}
            onChange={setSelectedDepartment}
            placeholder="Select Department"
            className="text-black"
          />

          {/* Subject Dropdown */}
          <Select
            options={subjects}
            value={selectedSubject}
            onChange={setSelectedSubject}
            placeholder="Select Subject"
            className="text-black"
          />

          {/* Year Input */}
          <fieldset className="border border-gray-300 rounded-md p-4">
            <legend className="text-sm font-semibold text-white px-2">
              Year
            </legend>
            <input
              type="number"
              value={year}
              onChange={(e) => setYear(e.target.value)}
              placeholder="Ex: 2024"
              className="w-full px-4 py-1.5 rounded-[12px] bg-white text-black focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </fieldset>

          {/* File Upload */}
          <div>
            <label className="block text-sm mb-2">Attach Files (PDF*)</label>
            <input
              type="file"
              accept="application/pdf"
              onChange={(e) => setFile(e.target.files[0])}
              ref={fileInputRef}
              className="w-full rounded-[12px] px-4 py-1.5 bg-white text-black focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-btngreen custom-shadow text-white font-semibold text-2xl py-2 rounded-[12px] hover:bg-green-700 transition-all"
          >
            Submit
          </button>
        </form>
      </div>
    )
  );
};

export default PYQUpload;
