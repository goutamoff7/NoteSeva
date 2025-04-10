import React from "react";
import ProfileCard from "../components/ProfileCard";
import LikeGains from "../components/LikeGains";
import { IoSettingsSharp } from "react-icons/io5";
import { CiLogout } from "react-icons/ci";
import BadgeProgress from "../components/BadgeProgress";
import { useState } from "react";
import UploadSection from "../components/UploadSection";
import ProjectSection from "../components/ProjectSection";
import UploadNote from "../components/UploadNote";
import MapStyleGrid from "../components/MapStyleGrid";
// import CustomContributionChart from "../components/CustomContributionChart";
const Dashboard = () => {
  const [count, setCount] = useState(0);
  return (
    <section className="grid grid-cols-[300px_auto_300px] bg-[#1E293B] min-h-screen p-4 ">
      {/* left side */}
      <div className="bg-[#001D31]  m-4 border border-gray-100 p-4 rounded-2xl flex flex-col gap-2">
        <ProfileCard />
        <LikeGains />
        <div className="flex flex-col gap-2 items-center text-white text-xl mt-5">
          <button className="flex gap-2 flex-row items-center justify-center w-full  py-1 border border-[#F0F6FC1A] bg-[#263238] rounded-lg">
            <p>Logout</p>
            <CiLogout />
          </button>
          <button className="flex gap-2 flex-row items-center justify-center w-full py-1 border border-[#F0F6FC1A] bg-[#263238] rounded-lg mt-2 mb-8">
            <p>Settings</p>{" "}
            <span>
              <IoSettingsSharp />
            </span>
          </button>
        </div>
      </div>
      {/* middle side */}
      <div className=" rounded-md mt-4">
        <div className="flex gap-2 flex-row">
          <UploadSection />
          <ProjectSection />{" "}
        </div>
        <MapStyleGrid />
        <UploadNote />
      </div>
      {/* right side */}
      <div className="m-4">
        <BadgeProgress uploadCount={count} />
      </div>
    </section>
  );
};

export default Dashboard;
