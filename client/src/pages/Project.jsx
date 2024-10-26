import React from "react";
import { CiSearch } from "react-icons/ci";
import ProjectCard from "../components/ProjectCard";
import Footer from "../components/Footer";
import Filter from "../components/Filter";

const projects = [
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
  {
    title: "Weather app",
    para: "description ",
    image: "Project_image.png",
    userName: "NAME",
    rating: "5",
    isVerified: "true",
  },
];
const Project = () => {
  return (
    <section className="w-full flex flex-col justify-center items-center ">
      <div className="  bg-darkbg bg-opacity-60 w-full bg-no-repeat flex justify-center overflow-hidden items-center">
        <div className="w-full h-[820px] flex flex-col justify-center items-center gap-10 bg-backGroundImg bg-no-repeat object-fill  ">
          <h1 className="font-poppins font-bold text-6xl text-center text-white_1 w-[550px] leading-tight px-10 z-50 ">
            More than just a <span className="text-emerald-400">Project</span>{" "}
            It’s a lifestyle
          </h1>
          <p className="text-white_1  w-[400px] text-center">
            Driven by passion, fuelled by innovation, shaping tomorrow's
            possibilities.
          </p>
          <div className="flex items-center gap-10">
            <button className="w-40 h-10 rounded-lg bg-btngreen text-whitee ">
              Explore
            </button>
            <button className="w-40 h-10 rounded-lg bg-midblue text-whitee ">
              Add
            </button>
          </div>
        </div>
      </div>

      <main className="bg-[url('darkbg.png')] w-full max-h-full flex flex-col justify-center items-center gap-8 px-20 ">
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
        {/* filtersection */}
        <Filter />
        {/* card section */}
        <div className="grid grid-cols-3 gap-10 mt-14">
          {projects.map((project, index) => (
            <ProjectCard
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
