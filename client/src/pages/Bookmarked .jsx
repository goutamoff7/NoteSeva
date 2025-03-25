import React, { useState, useEffect } from "react";
import NoteCard from "../components/NoteCard";
import { userNoteData } from "../../data/data";

const Bookmarked = () => {
  const [activeItem, setActiveItem] = useState("all");

  // Set "all" as default active item when component mounts
  useEffect(() => {
    setActiveItem("all");
  }, []);

  const handleClick = (item) => {
    setActiveItem(item);
  };

  const handleDoubleClick = () => {
    setActiveItem("all");
  };

  return (
    <div className="w-full min-h-screen flex flex-row">
      <ul className=" w-[20%] bg-[#343E4F] flex flex-col h-screen gap-6 pt-16 text-white px-6 ">
        <li
          className={` flex items-center gap-4 px-4  cursor-pointer ${
            activeItem === "all"
              ? "bg-btngreen h-8 rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("all")}
          onDoubleClick={handleDoubleClick}
        >
          <p className="">All</p>
        </li>

        <li
          className={` flex items-center gap-4 px-4  cursor-pointer ${
            activeItem === "notes"
              ? "bg-btngreen h-8 rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("notes")}
          onDoubleClick={handleDoubleClick}
        >
          <svg
            className="w-6 h-6"
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
          <p className="cursor-pointer">Notes</p>
        </li>

        <li
          className={` flex items-center gap-4 px-4  cursor-pointer ${
            activeItem === "pyq"
              ? "bg-btngreen h-8 w-full rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("pyq")}
          onDoubleClick={handleDoubleClick}
        >
          <svg
            className="w-6 h-6"
            xmlns="http://www.w3.org/2000/svg"
            width="98.47"
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
          <p className="cursor-pointer">PYQ</p>
        </li>

        <li
          className={` flex items-center gap-4 px-4 cursor-pointer ${
            activeItem === "organiser"
              ? "bg-btngreen h-8 w-full rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("organiser")}
          onDoubleClick={handleDoubleClick}
        >
          <svg
            className="w-6 h-6"
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
          <p className="cursor-pointer">Organiser</p>
        </li>
      </ul>

      <div className="bg-darkbg w-[80%] p-6 grid grid-cols-3 gap-5 2xl:grid-cols-4">
        {userNoteData.map((note) => (
          <NoteCard
            id={note.index}
            title={note.title}
            subject={note.subject}
            userName={note.userName}
            noteImage={note.noteImage}
            userImage={note.userImage}
          />
        ))}
      </div>
    </div>
  );
};

export default Bookmarked;
