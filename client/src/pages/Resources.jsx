import React,{useState} from "react";
import Select from "react-select";
import { notesUploadOptions } from "../../data/data.js";
import { useAppContext } from "../context/AppContext.jsx";

const Resources = () => {
  const { courses, departments, subjects } = notesUploadOptions;

  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);

  const {apiClient,isAuthenticated} = useAppContext()


  return (
    isAuthenticated && (
      <div className="w-full min-h-screen flex">
        
        <div className="w-[25%] bg-[#343E4F] flex flex-col space-y-5 pt-10 px-2">

          {/* Courses dropdown */}
          <div className="">
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
          <div className="">
            <Select
              options={departments}
              value={selectedDepartment}
              onChange={setSelectedDepartment}
              placeholder="Select Department"
              className="text-black"
            />
          </div>

          {/* Subject dropdown */}
          <div className="">
            <Select
              options={subjects}
              value={selectedSubject}
              onChange={setSelectedSubject}
              placeholder="Select Subject"
              className="text-black"
            />
          </div>

        </div>

        <div className="bg-darkbg w-full">
          
          <div className="flex flex-col items-center justify-center h-full space-y-5">
            <img src="/amico.png" alt="" className="w-[450px]" />
            <p className="text-white font-semibold text-3xl text-center">
              <span className="text-btngreen">Search</span> Your Requirement <br /> in quick and simple words
            </p>
          </div>

        </div>
      </div>
    )
  );
};

export default Resources;
