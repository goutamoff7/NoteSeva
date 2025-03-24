// src/components/CourseCard.js
import React from "react";
import { useNavigate } from "react-router-dom";

const CourseCard = ({ image, title }) => {
  const navigate = useNavigate();

  const handleContinue = () => {
    navigate('/features');
  };

  return (
    <div className="bg-whitee rounded-lg shadow-md overflow-hidden">
      <img
        src={image}
        alt={title}
        className="w-full h-40 object-cover p-3 rounded-3xl"
      />
      <div className="p-4 text-center">
        <h3 className="text-xl font-bold text-darkblue mb-2">{title}</h3>
        <button 
        onClick={handleContinue}
        className="bg-btngreen text-whitee py-2 px-4 rounded hover:bg-green-600">
          Check Now
        </button>
      </div>
    </div>
  );
};

export default CourseCard;
