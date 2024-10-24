import React, { useState } from "react";
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

const ProfileCard = () => {
  // Array of student profiles
  const students = [
    {
      id: 1,
      name: "Manojit Das",
      image: "Manojit.jpg",
      dev: "Full Stack Web Developer",
      Linkedin: "https://www.linkedin.com/in/manojit-das-10-/",
      GitHub: "https://github.com/Manojit-Das-10",
      X: "https://twitter.com/Manojit_Das_10",
      Facebook: "https://www.facebook.com/manojit.das.9083",
      Instagram: "https://www.instagram.com/code_with_manojit/",
    },
    {
      id: 2,
      name: "Md Ramij Zamadar",
      image: "ramij.jpeg",
      dev: "Full Stack Developer",
      Linkedin: "https://www.linkedin.com/in/md-ramij-zamadar/",
      GitHub: "https://github.com/mdramijzamadar",
      X: "#",
      Facebook: "#",
      Instagram: "#",
    },
    {
      id: 3,
      name: "Goutam Dam",
      image: "Goutam.jpeg",
      dev: "Java Backend Developer",
      Linkedin: "https: //linkedin.com/in/goutamoff7",
      GitHub: "https://github.com/goutamoff7",
      X: "#",
      Facebook: "#",
      Instagram: "#",
    },
    {
      id: 4,
      name: "Arpan Kundu",
      image: "Arpan.jpeg",
      dev: "Java Backend Developer",
      Linkedin: " https://www.linkedin.com/in/kunduarpan",
      GitHub: "https://github.com/kundu-A ",
      X: "#",
      Facebook: "https://www.facebook.com/arpan.kundu.52459",
      Instagram: "https://www.instagram.com/arpan.kundu.52459/",
    },
  ];

  const [currentIndex, setCurrentIndex] = useState(0); // Manage current displayed student
  const [showShareOptions, setShowShareOptions] = useState(false); // Manage share options display
  const [fade, setFade] = useState(false); // Add fade state

  const toggleShareOptions = () => {
    setShowShareOptions(!showShareOptions);
  };

  // Function to go to the previous student
  const handlePrevious = () => {
    setFade(true); // Start fade-out
    setTimeout(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === 0 ? students.length - 1 : prevIndex - 1
      );
      setFade(false); // Fade back in
    }, 400); // Delay to match the transition duration
  };

  // Function to go to the next student
  const handleNext = () => {
    setFade(true); // Start fade-out
    setTimeout(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === students.length - 1 ? 0 : prevIndex + 1
      );
      setFade(false); // Fade back in
    }, 400); // Delay to match the transition duration
  };

  return (
    <div className="relative flex flex-col items-center justify-center my-[60px] space-y-[40px]">
      <h2 className="text-whitee text-4xl font-semibold ">Our Testimonials</h2>

      {/* Card Container */}
      <div
        className={`bg-[#ffffff1a] text-white p-6 rounded-lg shadow-lg w-[500px] h-[350px] relative transition-opacity duration-500 ${
          fade ? "opacity-0" : "opacity-100"
        }`} // Add transition and conditional class
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
        <p className="text-center text-gray-400">{students[currentIndex].dev}</p>

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
            <a href={students[currentIndex].Linkedin} className="text-[#0A66C2]">
              <FaLinkedin size={30} />
            </a>
            <a href={students[currentIndex].GitHub} className="text-[#171515]">
              <FaGithub size={30} />
            </a>
            <a href={students[currentIndex].X} className="text-[#171515]">
              <FaXTwitter size={30} />
            </a>
            <a href={students[currentIndex].Facebook} className="text-[#1877F2]">
              <FaFacebook size={30} />
            </a>
            <a
              href={students[currentIndex].Instagram}
              className="text-pink-600"
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
