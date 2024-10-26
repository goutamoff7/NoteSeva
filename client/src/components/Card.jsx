import React from "react";
import { Link } from "react-router-dom";

const Card = ({ title, para }) => {
  return (
    <Link className="bg-white_1  max-w-[263px] border-black flex flex-col justify-center items-center gap-5 rounded-2xl py-10 px-5">
      <div className="w-12 h-12 bg-tech_bg flex justify-center items-center rounded-sm">
        <img src="tech.png" alt="" className="w-8 h-8" />
      </div>
      <h2 className="font-bold text-xl">{title}</h2>
      <p className="text-xs font-light text-center">{para}</p>
      <button className="border border-black px-4 py-1 text-center font-normal rounded-3xl">
        Continue
      </button>
    </Link>
  );
};

export default Card;
