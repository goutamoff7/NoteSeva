import React from "react";

const Filter = ({ selectedTech, setSelectedTech }) => {
  const techFilters = ["All", "WebDev", "Android", "AI/ML", "Blockchain", "Cyber Security", "Data Science"];

  return (
    <div className="w-full flex flex-wrap justify-center mx-auto max-w-max space-x-6">
      {techFilters.map((tech) => (
        <button
          key={tech}
          onClick={() => setSelectedTech(tech)}
          className={`px-8 py-2 rounded-2xl font-semibold text-xl text-white_2 
            ${selectedTech === tech ? "bg-text_blue2" : "bg-red-50"} 
            hover:bg-blue-700 hover:text-whitee transition duration-700`}
        >
          {tech}
        </button>
      ))}
    </div>
  );
};

export default Filter;
