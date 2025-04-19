import React, { useState } from "react";
import { CiClock2 } from "react-icons/ci";

const UploadNote = () => {
  const [activeFilter, setActiveFilter] = useState("Recent Upload");

  const filters = ["Recent Upload", "organizer", "PYQ", "Notes"];
  const uploads = [
    { title: "Two Sum", time: "21 hours ago", category: "PYQ" },
    { title: "Two Sum", time: "21 hours ago", category: "organizer" },
    { title: "Two Sum", time: "21 hours ago", category: "Notes" },
  ];

  const filteredUploads =
    activeFilter === "Recent Upload"
      ? uploads
      : uploads.filter((u) => u.category === activeFilter);

  return (
    <div className="bg-[#0c1b2a] text-white p-4 rounded-2xl border border-gray-500 w-full">
      {/* Header */}
      <div className="flex justify-between items-center mb-4">
        <div className="flex gap-3">
          {filters.map((filter) => (
            <button
              key={filter}
              className={`px-3 py-1 rounded-full text-sm border ${
                activeFilter === filter
                  ? "bg-white text-black font-semibold"
                  : "bg-transparent text-white border-gray-400"
              }`}
              onClick={() => setActiveFilter(filter)}
            >
              {filter === "Recent Upload" ? (
                <span className="inline-flex items-center gap-1">
                  <CiClock2 size={14} />
                  {filter}
                </span>
              ) : (
                filter
              )}
            </button>
          ))}
        </div>
        <button className="text-sm text-blue-400 hover:underline">
          View All Uploads &gt;
        </button>
      </div>

      {/* Upload List */}
      <div className="space-y-3">
        {filteredUploads.map((upload, index) => (
          <div
            key={index}
            className="bg-[#1e293b] px-4 py-2 rounded-lg flex justify-between items-center"
          >
            <p>{upload.title}</p>
            <span className="text-sm text-gray-300">{upload.time}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UploadNote;
