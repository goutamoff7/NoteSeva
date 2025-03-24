import React from "react";
import { useNavigate } from "react-router-dom";

const Features = () => {
  const navigate = useNavigate();

  const items = [
    {
      title: "Notes",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="98.47"
          height="120"
          viewBox="0 0 20 26"
        >
          <path
            fill="currentColor"
            d="M13.41 0H2C.9 0 0 .9 0 2v22c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6.58zm2.09 20h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m0-3h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m.5-3.444c0 .248-.22.444-.5.444h-11c-.28 0-.5-.196-.5-.444v-3.11c0-.25.22-.446.5-.446h11c.28 0 .5.196.5.444zM15 7a2 2 0 0 1-2-2V1l6 6z"
          />
        </svg>
      ),
      description: "Access your notes here.",
    },
    {
      title: "PYQ",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="128"
          height="128"
          viewBox="0 0 32 32"
        >
          <g fill="none" stroke="currentColor" strokeWidth="1">
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M10 9h4m-4 7h12m-12 4h12m-12 4h4m-6 5h16a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H8a2 2 0 0 0-2 2v22a2 2 0 0 0 2 2"
            />
            <circle cx="22" cy="9" r=".5" fill="currentColor" />
          </g>
        </svg>
      ),
      description: "Previous year question papers.",
    },
    {
      title: "Organiser",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="128"
          height="128"
          viewBox="0 0 24 24"
        >
          <g
            fill="none"
            stroke="currentColor"
            strokeLinecap="round"
            strokeWidth="1.5"
          >
            <path d="M4 19V5a2 2 0 0 1 2-2h13.4a.6.6 0 0 1 .6.6v13.114M6 17h14M6 21h14" />
            <path strokeLinejoin="round" d="M6 21a2 2 0 1 1 0-4" />
            <path d="M9 7h6" />
          </g>
        </svg>
      ),
      description: "Organize your study materials.",
    },
  ];

  const handleContinue = (title) => {
    // Dynamic route based on title (lowercase for URL consistency)
    const path = `/features/${title.toLowerCase()}`;
    navigate(path);
  };

  return (
    <div className="w-full h-[80vh] xl:h-[87.5vh] bg-darkbg grid place-items-center px-20">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-10 p-5">
        {items.map((item, index) => (
          <div
            key={index}
            className="bg-white px-4 py-10 rounded-lg shadow-lg flex flex-col items-center text-center mx-3"
          >
            <div className="mb-4">{item.icon}</div>
            <h3 className="text-2xl font-bold mb-2">{item.title}</h3>
            <p className="text-gray-400 mb-12">{item.description}</p>
            <button
              className="bg-[#1E293B] text-white px-6 py-2 rounded-3xl hover:bg-[#1E293B]/80"
              onClick={() => handleContinue(item.title)}
            >
              Continue
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Features;