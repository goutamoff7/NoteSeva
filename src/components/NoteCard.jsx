import React, { useState, useEffect } from "react";
import { IoBookmark } from "react-icons/io5";
import { FaHeart } from "react-icons/fa";
import { BsThreeDotsVertical } from "react-icons/bs";
import * as pdfjsLib from "pdfjs-dist/build/pdf"; // Import PDF.js core

// Manually set the worker source
pdfjsLib.GlobalWorkerOptions.workerSrc =
  "/node_modules/pdfjs-dist/build/pdf.worker.js";

export default function NoteCard({
  id,
  title,
  subject,
  userImage,
  noteImage,
  userName,
  uploadDate,
  year,
  downloadLink
}) {

  const handleViewClick = () => {
    window.open(downloadLink, "_blank");
  };

  return (
    <div className="w-64 rounded-2xl shadow-lg overflow-hidden border">
      <div className="p-4">
        <div className="flex justify-between">
          <div className="font-bold text-lg text-white">{title || year}</div>
          <p className="text-white cursor-pointer">
            <BsThreeDotsVertical />
          </p>
        </div>
        <div className="text-gray-500 text-sm mb-2">{subject}</div>

        <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex items-center justify-center">
          <img
            src={noteImage ||"/notes.webp"}
            alt="Note Preview"
            className="object-cover h-full w-full"
          />
        </div>

        <div className="flex justify-between items-center mt-4">
          <button
            onClick={handleViewClick}
            className="bg-green-500 text-white hover:bg-green-600 px-4 py-2 rounded-lg"
          >
            View
          </button>

          <div className="flex items-center space-x-2">
            <FaHeart className="w-5 h-5 text-red-500" />
            <span className="text-gray-500">3.2k</span>
            <IoBookmark className="w-5 h-5 text-gray-500" />
          </div>
        </div>

        <div className="mt-4 flex items-center space-x-2">
          <img
            src={userImage || "/upload_area.png"}
            alt="Profile"
            className="w-8 h-8 rounded-full object-cover"
          />
          <div>
            <div className="font-semibold text-sm text-white">{userName}</div>
            <div className="text-gray-400 text-xs">{uploadDate}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
