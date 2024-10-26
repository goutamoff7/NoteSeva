import React from "react";
import { Link } from "react-router-dom";

const Card = ({ title, para , image}) => {
  return (
    <Link className="bg-[#dbe1e3]  w-[250px] flex flex-col justify-center items-center gap-5 rounded-2xl py-10 px-5">
      <div className="w-12 h-12 flex justify-center items-center rounded-sm">
        <img src={image} alt="" className="w-10 h-10" />
      </div>
      <h2 className="font-bold text-xl">{title}</h2>
      <p className="text-xs font-light text-center">
        {para}
        <br />
        🎓
        </p>
      <button className="bg-btngreen text-whitee font-semibold px-4 py-1 text-center rounded-3xl hover:bg-green-500">
        Continue
      </button>
    </Link>
  );
};

export default Card;
