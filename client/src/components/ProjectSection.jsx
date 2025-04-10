import React from "react";
import { FaStar, FaRegStar } from "react-icons/fa";
import { GrAddCircle } from "react-icons/gr";
const ProjectSection = () => {
  const projects = [
    { name: "NoteSeva", rating: 3 },
    { name: "NoteSeva", rating: 4 },
    { name: "Mediconnect", rating: 5 },
    { name: "MindGlow", rating: 5 },
  ];

  const renderStars = (rating) => {
    return (
      <div className="flex text-yellow-400">
        {[...Array(5)].map((_, i) =>
          i < rating ? <FaStar key={i} /> : <FaRegStar key={i} />
        )}
      </div>
    );
  };

  return (
    <div className="bg-[#0c1b2a] text-white rounded-2xl p-4 w-full max-w-sm shadow-lg border border-[#4b5563]">
      <h3 className="text-md font-semibold underline decoration-white mb-4">
        Projects
      </h3>
      <div className="space-y-3">
        {projects.map((proj, idx) => (
          <div
            key={idx}
            className="bg-[#1a2b3c] px-4 py-2 rounded-lg flex items-center justify-between"
          >
            <div className="flex items-center space-x-2">
              <div className="text-gray-400 text-sm">
                <GrAddCircle />
              </div>
              <p>{proj.name}</p>
            </div>
            {renderStars(proj.rating)}
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProjectSection;
