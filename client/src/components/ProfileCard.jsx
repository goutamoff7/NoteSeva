import React from "react";
import { FaInstagram, FaGithub, FaLinkedin,FaGlobe } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useAppContext } from "../context/AppContext";

const ProfileCard = () => {
  const navigate = useNavigate();
  const { userInfo } = useAppContext();

  const handleClick = () => {
    navigate("/my-profile");
  };

  return (
    <div className="flex flex-col items-center justify-between bg-[#1E293B] border border-gray-100 text-white p-4 rounded-2xl gap-4">
      <div className="w-16 h-16 rounded-full shadow-lg overflow-hidden">
        <img src={userInfo.imageUrl} className="w-full" alt="" />
      </div>
      <div className="text-center">
        <h3>{userInfo.name}</h3>
        <p>{userInfo.role.replace("ROLE_", "")}</p>
      </div>
      <div className="flex flex-row gap-4 text-3xl items-center">
        <a
          href={userInfo.gitHubUrl}
          className="hover:text-green-500"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FaGithub />
        </a>

        <a
          href={userInfo.linkedInUrl}
          className="hover:text-green-500"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FaLinkedin />
        </a>

        <a
          href={userInfo.otherUrl}
          className="hover:text-green-500"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FaGlobe  />
        </a>
      </div>
      <button
        onClick={handleClick}
        className="bg-[#263238] border rounded-md border-[#F0F6FC1A] px-6 py-1"
      >
        Edit Profile
      </button>
    </div>
  );
};

export default ProfileCard;
