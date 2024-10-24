import React, { useState } from 'react';
import Select from 'react-select';

const NotesUpload = () => {
  // Mock data for the dropdowns
  const coursesOptions = [
    { value: 'btech', label: 'B.Tech' },
    { value: 'bpharm', label: 'B.Pharm' },
    { value: 'nurshing', label: 'Nurshing' }
  ];

  const departmentOptions = [
    { value: 'cse', label: 'Computer Science' },
    { value: 'ee', label: 'Electrical Engineering' },
    { value: 'me', label: 'Mechanical Engineering' }
  ];

  const subjectOptions = [
    { value: 'os', label: 'Operating System' },
    { value: 'se', label: 'Software Engineering' },
    { value: 'cn', label: 'Computer Network' }
  ];

  // State for the form fields
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [selectedSubject, setSelectedSubject] = useState(null);

  return (
    <div className="min-h-screen flex items-center justify-evenly bg-gray-900 text-white">
      <div className='max-w-[400px] space-y-[30px]'>
        <img src="/notesupload.png" alt=""  className='h-[300px] w-[380px]'/>
        <h2 className='font-bold text-whitee text-2xl leading-[40px] text-center'>
          <span className='text-btngreen'>Notes</span> are chits created by your brain for exam
        </h2>
      </div>
      <div className="w-full max-w-md space-y-[20px]">
        <h2 className="text-2xl font-semibold mb-6 text-whitee">Notes Upload</h2>
        {/* Courses dropdown */}
        <div className="">
          <Select
            options={coursesOptions}
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
            options={departmentOptions}
            value={selectedDepartment}
            onChange={setSelectedDepartment}
            placeholder="Select Department"
            className="text-black"
          />
        </div>
        {/* Subject dropdown */}
        <div className="">
          <Select
            options={subjectOptions}
            value={selectedSubject}
            onChange={setSelectedSubject}
            placeholder="Select Subject"
            className="text-black"
          />
        </div>
        {/* Topic input */}
        <div className="">
          <label className="block text-sm mb-2">Topic Name</label>
          <input
            type="text"
            placeholder="Ex: Deadlock"
            className="w-full p-3 rounded-[12px] bg-gray-800 text-white focus:outline-none focus:ring-2 focus:ring-green-500"
          />
        </div>
        {/* Attach files */}
        <div className="">
          <label className="block text-sm mb-2">Attach Files (PDF*)</label>
          <input
            type="file"
            accept="application/pdf"
            className="w-full rounded-[12px] p-3 bg-gray-800 text-white focus:outline-none"
          />
        </div>

        {/* Submit Button */}
        <div className="">
            <button className="w-full bg-btngreen custom-shadow text-white font-semibold text-2xl p-3 rounded-[12px] hover:bg-green-700 transition-all">
              Submit
            </button>
          </div>

      </div>
    </div>
  );
};

export default NotesUpload;
