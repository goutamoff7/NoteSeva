// src/components/FeatureCard.js
import React from 'react';

const FeatureCard = ({ icon, title, description }) => {
  return (
    <div className="relative bg-whitee rounded-lg shadow-lg mt-[20px] text-center w-[350px] h-[230px] pt-[60px]">
      <div className="flex justify-center items-center absolute -top-12 left-32">
        <img src={icon} alt={title} className="w-[100px] h-[100px] rounded-full p-2" />
      </div>
      <h3 className="text-xl font-bold text-darkblue mb-2">{title}</h3>
      <p className="text-darkblack text-sm px-[30px]">{description}</p>
    </div>
  );
};

export default FeatureCard;
