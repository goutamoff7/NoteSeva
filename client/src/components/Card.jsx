import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Card = ({ title, para, image }) => {
  const navigate = useNavigate();

  const handleContinue = () => {
    navigate(`/course/${title}/features`);
  };

  return (
    <div className="bg-[#dbe1e3] w-[250px] flex flex-col justify-center items-center gap-5 rounded-2xl py-10 px-5">
      <div className="w-12 h-12 flex justify-center items-center rounded-sm">
        <img src={image} alt="" className="w-10 h-10" />
      </div>
      <h2 className="font-bold text-xl">{title}</h2>
      <p className="text-xs font-light text-center">
        {para}
        <br />
        🎓
      </p>
      <button
        onClick={handleContinue}
        className="bg-btngreen text-white font-semibold px-4 py-1 text-center rounded-3xl hover:bg-green-500"
      >
        Continue
      </button>
    </div>
  );
};

export default Card;
