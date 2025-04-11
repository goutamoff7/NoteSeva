import React from "react";
import { FaInstagram, FaGithub, FaLinkedin } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const ProfileCard = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/my-profile");
  };

  return (
    <div className="flex flex-col items-center justify-between bg-[#1E293B] border border-gray-100 text-white p-4 rounded-2xl gap-4">
      <div className="w-16 h-16 rounded-md shadow-lg overflow-hidden">
        <img src="Manojit.jpg" className="w-full" alt="" />
      </div>
      <div className="text-center">
        <h3>John Doe</h3>
        <p>Student</p>
      </div>
      <div className="flex flex-row gap-4 text-3xl">
        <FaGithub />
        <FaInstagram />
        <FaLinkedin />
      </div>
      <button
        onClick={handleClick}
        className="bg-[#263238] border border-[#F0F6FC1A] px-6 py-1"
      >
        Edit Profile
      </button>
    </div>
  );
};

export default ProfileCard;
