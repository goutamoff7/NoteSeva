import React, { useState, useEffect } from "react";
import {
  FaGithub,
  FaLinkedin,
  FaFacebook,
  FaInstagram,
  FaArrowLeft,
  FaArrowRight,
  FaTimes,
} from "react-icons/fa";
import { FaShare, FaXTwitter } from "react-icons/fa6";
import { students } from "../../../data/data";

const ProfileCard = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [showShareOptions, setShowShareOptions] = useState(false);
  const [fade, setFade] = useState(false);
  const [isHovered, setIsHovered] = useState(false); // New state for hover

  const toggleShareOptions = () => {
    setShowShareOptions(!showShareOptions);
  };

  // Function to go to the next student
  const goToNext = () => {
    setFade(true);
    setTimeout(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === students.length - 1 ? 0 : prevIndex + 1
      );
      setFade(false);
    }, 400);
  };

  const handlePrevious = () => {
    setFade(true);
    setTimeout(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === 0 ? students.length - 1 : prevIndex - 1
      );
      setFade(false);
    }, 400);
  };

  const handleNext = () => {
    goToNext();
  };

  // Auto-rotation effect with pause on hover or share options visible
  useEffect(() => {
    let interval;

    // Only run interval if not hovered and share options are not visible
    if (!isHovered && !showShareOptions) {
      interval = setInterval(() => {
        goToNext();
      }, 4000);
    }

    // Cleanup interval on unmount or when conditions change
    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  }, [isHovered, showShareOptions]);

  return (
    <div className="relative flex flex-col items-center justify-center my-[60px] space-y-[40px]">
      <h2 className="text-whitee text-4xl font-semibold ">Our Testimonials</h2>

      {/* Card Container */}
      <div
        className={`bg-[#ffffff1a] text-white p-6 rounded-lg shadow-lg w-[500px] h-[350px] relative transition-opacity duration-500 ${
          fade ? "opacity-0" : "opacity-100"
        }`}
        onMouseEnter={() => setIsHovered(true)} // Pause on hover
        onMouseLeave={() => setIsHovered(false)} // Resume on hover out
      >
        {/* Left Arrow */}
        <button
          onClick={handlePrevious}
          className="absolute top-1/2 left-0 transform -translate-y-1/2 p-2"
        >
          <FaArrowLeft className="text-white text-2xl" />
        </button>

        {/* Student Data */}
        <div className="flex justify-center mb-4">
          <img
            src={students[currentIndex].image}
            alt={students[currentIndex].name}
            className="rounded-full w-[150px] h-[150px] scale-105 custom-shadow"
          />
        </div>

        {/* Profile Name */}
        <h2 className="text-center text-2xl font-semibold">
          {students[currentIndex].name}
        </h2>
        <p className="text-center text-gray-400">
          {students[currentIndex].dev}
        </p>

        {/* Share Profile Button */}
        <div className="flex justify-center mt-4">
          <button
            className="flex justify-between items-center bg-transparent w-[180px] border border-gray-500 py-2 px-4 rounded-md hover:bg-whitee hover:text-black transition"
            onClick={toggleShareOptions}
          >
            <FaShare /> Share My Profile
          </button>
        </div>

        {/* Share Options - Conditional Rendering */}
        {showShareOptions && (
          <div className="w-[90%] bg-gray-200 border border-gray-500 mt-4 p-4 rounded-lg absolute top-[70%] z-10 flex justify-evenly">
            <button
              className="absolute top-0 right-0 mt-2 mr-2"
              onClick={() => setShowShareOptions(false)}
            >
              <FaTimes className="text-darkblack text-xl" />
            </button>
            <a
              href={students[currentIndex].linkedin}
              className="text-[#0A66C2]"
              target="_blank"
              rel="noopener noreferrer"
            >
              <FaLinkedin size={30} />
            </a>
            <a
              href={students[currentIndex].github}
              className="text-[#171515]"
              target="_blank"
              rel="noopener noreferrer"
            >
              <FaGithub size={30} />
            </a>
            <a
              href={students[currentIndex].x}
              className="text-[#171515]"
              target="_blank"
              rel="noopener noreferrer"
            >
              <FaXTwitter size={30} />
            </a>
            <a
              href={students[currentIndex].facebook}
              className="text-[#1877F2]"
              target="_blank"
              rel="noopener noreferrer"
            >
              <FaFacebook size={30} />
            </a>
            <a
              href={students[currentIndex].instagram}
              className="text-pink-600"
              target="_blank"
              rel="noopener noreferrer"
            >
              <FaInstagram size={30} />
            </a>
          </div>
        )}

        {/* Right Arrow */}
        <button
          onClick={handleNext}
          className="absolute top-1/2 right-0 transform -translate-y-1/2 p-2"
        >
          <FaArrowRight className="text-white text-2xl" />
        </button>
      </div>
    </div>
  );
};

export default ProfileCard;
