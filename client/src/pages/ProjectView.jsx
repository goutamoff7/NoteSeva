import React from "react";
import ProjectCard from "../components/ProjectCard";
const relatedProjects = [
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
const ProjectView = () => {
  return (
    <section className="bg-darkbg p-10 ">
      <div>
        <div className=" object-cover overflow-hidden ">
          <img src="/project_banner.png" alt="" className="w-full mx-auto " />
        </div>
        <h2 className="font-semibold text-4xl text-white_1 mt-10">
          Weather App created using java and Android Studio
        </h2>
        <div className="flex flex-row gap-1 py-3 text-whitee justify-between">
          {/* icon */}
          <span className="uppercase font-bold text-xl tracking-wider">
            name
          </span>
          <p>date</p>
        </div>
        <p className="text-whitee text-xl">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut ab nihil
          odio quas provident in vel reiciendis qui delectus deleniti. Vero
          asperiores eius commodi dolores! Soluta, saepe illo vero numquam
          labore ratione deserunt officiis omnis quod repellendus quia explicabo
          velit, expedita nam cum aliquid. Fugit non est et recusandae, sit
          obcaecati similique neque quisquam impedit eum ducimus autem deleniti
          magnam ipsam repudiandae nemo esse error. Voluptate velit non
          perferendis molestias id quaerat, nesciunt, architecto sapiente,
          reprehenderit esse facere blanditiis dolorem! Lorem ipsum dolor sit
          amet consectetur adipisicing elit. Quos, dolorem sed odio culpa
          ducimus quis eligendi necessitatibus sint consequatur distinctio
          tempore laborum illum? Optio cupiditate laborum veniam nisi ipsa
          nesciunt ad, beatae suscipit, ab non est quos. Odio voluptatibus, sint
          soluta deleniti sapiente placeat nostrum quam molestias, dolor eaque
          consequuntur distinctio? Animi quae dolorum, nesciunt maiores
          perspiciatis vero sapiente impedit dolores! Et, adipisci! Illum
          inventore ad sapiente recusandae obcaecati? Exercitationem temporibus
          quaerat quasi provident obcaecati officiis recusandae maiores
          incidunt, blanditiis atque. Facilis ad repellat placeat. Officiis
          repudiandae iste quis sint.
        </p>
      </div>
      <div className="">
        <h3 className="text-white text-5xl uppercase text-center mt-10 underline">
          Related Projects
        </h3>
        <div className="grid grid-cols-3 gap-10 mt-14">
          {relatedProjects.map((project, index) => (
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
      </div>
    </section>
  );
};

export default ProjectView;
