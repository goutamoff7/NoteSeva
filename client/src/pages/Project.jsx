import React, { useState, useRef } from "react";
import { CiSearch } from "react-icons/ci";
import ProjectCard from "../components/ProjectCard";
import Footer from "../components/Footer";
import Filter from "../components/Filter";
import { Link } from "react-router-dom";

const projects = [
  {
    id: "1",
    title: "Weather App",
    para: "description",
    image: "project_banner.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "Android",
  },
  {
    id: "2",
    title: "Portfolio Website",
    para: "description",
    image: "portfolio.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "WebDev",
  },
  {
    id: "3",
    title: "AI Chatbot",
    para: "description",
    image: "aichatbot.jpg",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "AI/ML",
  },
  {
    id: "4",
    title: "Blockchain Voting System",
    para: "description",
    image: "blockchain.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "Blockchain",
  },
  {
    id: "5",
    title: "Cyber Security Analyzer",
    para: "description",
    image: "cybersecurity.jpeg",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "Cyber Security",
  },
  {
    id: "6",
    title: "Data Science Dashboard",
    para: "description",
    image: "datascience.avif",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
    tech: "Data Science",
  },
];

const Project = () => {
  const [selectedTech, setSelectedTech] = useState("All"); // Default to "All"
  const exploreRef = useRef(null); // Create a reference for the "Explore Projects" section

  // Function to scroll to the explore section
  const handleExploreClick = () => {
    exploreRef.current.scrollIntoView({ behavior: "smooth" });
  };

  // Filter projects based on selected tech
  const filteredProjects = selectedTech === "All"
    ? projects
    : projects.filter((project) => project.tech === selectedTech);

  return (
    <section className="w-full flex flex-col justify-center items-center ">
      {/* Hero section */}
      <div className="  bg-darkbg bg-opacity-60 w-full bg-no-repeat flex justify-center overflow-hidden items-center">
        <div className="bg-[url('project-frame.png')] w-full h-[90vh] flex flex-col justify-center items-center gap-10 bg-no-repeat object-fill">
          <h1 className="font-bold text-6xl text-center text-white_1 w-[550px] leading-tight px-10 z-50">
            More than just a <span className="text-emerald-400">Project</span>{" "}
            It’s a lifestyle
          </h1>
          <p className="text-white_1  w-[400px] text-center">
            Driven by passion, fuelled by innovation, shaping tomorrow's
            possibilities.
          </p>
          <div className="flex items-center gap-10">
            <button 
              className="w-40 h-10 text-xl font-semibold rounded-lg bg-btngreen text-whitee custom-shadow"
              onClick={handleExploreClick} // Scroll down to the explore section
            >
              Explore
            </button>

            <Link to="/contribute/projectUpload">
              <button className="w-40 h-10 text-xl font-semibold rounded-lg bg-midblue text-whitee custom-shadow">
                Add
              </button>
            </Link>
          </div>
        </div>
      </div>

      <main 
        className="bg-darkbg w-full max-h-full flex flex-col justify-center items-center gap-8 px-20 pb-[100px]"
        ref={exploreRef} // Attach the reference to this section
      >
        <span className=" w-full text-center text-whitee underline font-semibold text-5xl mt-14">
          Explore Projects
        </span>

        {/* search container */}
        <div className="relative mt-3">
          <input
            className="w-[380px] h-[50px] rounded-full pl-[40px] outline-none"
            type="text"
            placeholder="Search For Projects"
          />
          <CiSearch className="absolute top-4 left-4 h-[20px] w-[20px]" />
          <button className="absolute rounded-full top-1 right-1 bg-btngreen h-[42px] w-24 text-whitee">
            Continue
          </button>
        </div>

        {/* filter section */}
        <Filter selectedTech={selectedTech} setSelectedTech={setSelectedTech} />

        {/* card section */}
        <div className="grid grid-cols-3 gap-10 mt-14">
          {filteredProjects.map((project) => (
            <ProjectCard
              key={project.id}
              id={project.id}
              title={project.title}
              para={project.para}
              image={project.image}
              userName={project.userName}
              rating={project.rating}
              isVerified={project.isVerified}
            />
          ))}
        </div>
      </main>
      
      <Footer />
    </section>
  );
};

export default Project;
