import { useState } from "react";
import { FaDownload, FaEye, FaHeart } from "react-icons/fa";
import { GrLike } from "react-icons/gr";

const UploadNote = ({ userUploads, backendUrl, formatDate }) => {
  const [activeFilter, setActiveFilter] = useState("Notes");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  // Extracting different upload types
  const uploadsMap = {
    Notes: userUploads.notesDTOList || [],
    organizer: userUploads.organizerDTOList || [],
    PYQ: userUploads.pyqDTOList || [],
  };

  // Getting the filtered uploads based on active filter
  const filteredUploads = uploadsMap[activeFilter] || [];

  // Calculate pagination
  const totalPages = Math.ceil(filteredUploads.length / itemsPerPage);
  const paginatedUploads = filteredUploads.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  // Define filter options
  const filters = ["Notes", "organizer", "PYQ"];

  // Handle view action
  const handleViewClick = (upload) => {
    const viewLink = `${backendUrl}/${activeFilter.toLowerCase()}/get/${
      upload.id
    }?option=view`;
    window.open(viewLink, "_blank");
  };

  // Handle download action
  const handleDownload = (upload) => {
    const downloadLink = `${backendUrl}/${activeFilter.toLowerCase()}/get/${
      upload.id
    }?option=download`;
    const a = document.createElement("a");
    a.href = downloadLink;
    a.download = `${upload.topicName || upload.year || "note"}.pdf`;
    a.click();
  };

  return (
    <div className="bg-[#0c1b2a] text-white p-4 rounded-2xl border border-gray-500 w-full min-h-[40vh]">
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
              onClick={() => {
                setActiveFilter(filter);
                setCurrentPage(1); // Reset to first page on filter change
              }}
            >
              {filter}
            </button>
          ))}
        </div>
      </div>

      {/* Upload List */}
      <div className="space-y-3">
        <div className="px-4 py-2 grid grid-cols-[0.5fr_3fr_5fr_2fr_2fr_2fr_3fr] place-items-center">
          <p>#</p>
          <p>Topic Name</p>
          <p>Subject Name</p>
          <p>Likes</p>
          <p>View</p>
          <p>Download</p>
          <p>Upload Date</p>
        </div>
        {paginatedUploads.map((upload, index) => (
          <div
            key={upload.id || index}
            className="bg-[#1e293b] px-4 py-2 rounded-lg grid grid-cols-[0.5fr_3fr_5fr_2fr_2fr_2fr_3fr] place-items-center"
          >
            <p>{(currentPage - 1) * itemsPerPage + index + 1}</p>
            <p>{upload.topicName || upload.year || "No Title"}</p>
            <p>{upload.subjectName || "No Title"}</p>
            <p className="flex items-center gap-1">
              {upload.likeCount}
              <FaHeart />
            </p>
            <button
              onClick={() => handleViewClick(upload)}
              className="border px-3 py-1 rounded-full text-center w-fit"
            >
              <FaEye />
            </button>
            <button
              onClick={() => handleDownload(upload)}
              className="border px-3 py-1 rounded-full text-center w-fit"
            >
              <FaDownload />
            </button>
            <span className="text-sm text-gray-300">
              {formatDate(upload.uploadDateTime)}
            </span>
          </div>
        ))}
      </div>

      {/* Pagination Controls */}
      <div className="flex justify-center gap-2 mt-10">
        {/* Previous Button */}
        <button
          disabled={currentPage === 1}
          className={`px-3 py-1 rounded-full text-sm border ${
            currentPage === 1
              ? "text-gray-500 border-gray-500"
              : "text-white border-gray-400"
          }`}
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
        >
          Previous
        </button>

        {/* Current Page Number */}
        <span className="px-3 py-1 rounded-md text-sm border bg-white text-black font-semibold">
          {currentPage}
        </span>

        {/* Next Button */}
        <button
          disabled={currentPage === totalPages}
          className={`px-3 py-1 rounded-full text-sm border ${
            currentPage === totalPages
              ? "text-gray-500 border-gray-500"
              : "text-white border-gray-400"
          }`}
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default UploadNote;
