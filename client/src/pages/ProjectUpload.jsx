import React, { useState } from 'react';
import Select from 'react-select';
import { ToastContainer, toast } from 'react-toastify';

const ProjectUpload = () => {
  // Mock data for the dropdowns
  const technologyOptions = [
    { value: 'ai/ml', label: 'AI/ML' },
    { value: 'web', label: 'Web' },
    { value: 'android', label: 'Android' },
    { value: 'blockchain', label: 'Blockchain' },
    { value: 'cybersecurity', label: 'Cyber Security' },
    { value: 'datascience', label: 'Data Science' },
    { value: 'others', label: 'Others' },
  ];

  // State for the form fields
  const [selectedTechnology, setSelectedTechnology] = useState(null);
  const [teamMembers, setTeamMembers] = useState([{ name: '' }]);

  // Function to handle adding a new input field for team members
  const addNewMember = () => {
    setTeamMembers([...teamMembers, { name: '' }]);
    toast.success("New team member added!");
  };

  // Function to handle removing an input field
  const removeMember = (index) => {
    const newTeamMembers = [...teamMembers];
    newTeamMembers.splice(index, 1); // Remove the member at the specific index
    setTeamMembers(newTeamMembers);
    toast.error("One team member deleted");
  };

  // Function to handle changes in team member inputs
  const handleTeamMemberChange = (index, event) => {
    const newTeamMembers = [...teamMembers];
    newTeamMembers[index].name = event.target.value;
    setTeamMembers(newTeamMembers);
  };

  return (
    <div className="min-h-screen flex items-center justify-evenly bg-gray-900 text-white">
      <ToastContainer position="top-center" autoClose={2000} hideProgressBar={false} />
      
      {/* Left Part */}
      <div className='max-w-[400px] space-y-[30px]'>
        <img src="/projectsupload.png" alt="" className='h-[300px] w-[380px]' />
        <h2 className='font-bold text-white text-2xl leading-[40px] text-center'>
          <span className='text-midblue'>Projects</span> are the Proof and Fruit of your Learning
        </h2>
      </div>

      {/* Right Part */}
      <div className="w-full max-w-md space-y-[20px] my-[40px]">

        {/* <form action=""> */}
          <h2 className="text-2xl font-semibold mb-6 text-white">Projects Upload</h2>

          {/* Technology dropdown */}
          <div className="">
            <Select
              options={technologyOptions}
              value={selectedTechnology}
              onChange={setSelectedTechnology}
              placeholder="Choose Your Technology"
              className="text-black"
              required
            />
          </div>

          {/* Project Title */}
          <div className="">
            <label className="block text-sm mb-2">Project Title</label>
            <input
              type="text"
              placeholder="Ex:- Tic Tac Toe"
              className="w-full p-3 rounded-[12px] bg-gray-800 text-white focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* Project Description */}
          <div className="">
            <label className="block text-sm mb-2">Project Description</label>
            <textarea
              placeholder="Ex:Tic-Tac-Toe in Java is a simple game played on a 3x3 grid..."
              className="w-full p-3 rounded-[12px] bg-gray-800 text-white focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* Team Members */}
          <div className="">
            <label className="block text-sm mb-2">Team Members</label>
            {teamMembers.map((member, index) => (
              <div key={index} className='flex justify-between items-center space-x-2 mb-4'>
                <input
                  type="text"
                  value={member.name}
                  onChange={(event) => handleTeamMemberChange(index, event)}
                  placeholder="Ex:- John Doe"
                  className="w-[70%] p-3 rounded-[12px] bg-gray-800 text-white focus:outline-none focus:ring-2 focus:ring-green-500"
                />
                {index === 0 ? (
                  <button
                    type="button"
                    onClick={addNewMember}
                    className='bg-btngreen h-[40px] w-[100px] rounded-[12px] text-md font-medium custom-shadow text-center hover:bg-green-700'>
                    Add New
                  </button>
                ) : (
                  <button
                    type="button"
                    onClick={() => removeMember(index)}
                    className='bg-red-600 h-[40px] w-[100px] rounded-[12px] text-md font-medium custom-shadow text-center hover:bg-red-800'>
                    Delete
                  </button>
                )}
              </div>
            ))}
          </div>

          {/* Cover Pic */}
          <div className="">
            <label className="block text-sm mb-2">Attach Cover (PNG, JPG, JPEG*)</label>
            <input
              type="file"
              accept="image/png, image/jpeg, image/jpg"
              className="w-full p-3 bg-gray-800 text-white rounded-[12px] focus:outline-none"
            />
          </div>

          {/* Project Link */}
          <div className="">
            <label className="block text-sm mb-2">Project Link (URL)</label>
            <input
              type="url"
              placeholder="https://example.com"
              className="w-full p-3 bg-gray-800 text-white rounded-[12px] focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* Submit Button */}
          <div className="">
            <button className="w-full bg-btngreen custom-shadow text-white font-semibold text-2xl p-3 rounded-[12px] hover:bg-green-700 transition-all">
              Submit
            </button>
          </div>

        {/* </form> */}
      </div>
    </div>
  );
};

export default ProjectUpload;
