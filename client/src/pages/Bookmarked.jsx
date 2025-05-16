import React, { useState, useEffect } from "react";
import NoteCard from "../components/NoteCard";
import { IoBookmark, IoSearchOutline } from "react-icons/io5";
import Pagination from "../components/Pagination";
import { useAppContext } from "../context/AppContext";
import { useAllContext } from "../context/AllContext";

const Bookmarked = () => {
  const { userInfo, backendUrl } = useAppContext();
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 8;
  const [activeItem, setActiveItem] = useState("notes");
  const [filteredData, setFilteredData] = useState([]);
  const { formatDate } = useAllContext();
  // Filter data based on active item
  useEffect(() => {
    switch (activeItem) {
      case "notes":
        setFilteredData(userInfo.bookmarkedNotes || []);
        break;
      case "pyq":
        setFilteredData(userInfo.bookmarkedPyq || []);
        break;
      case "organizer":
        setFilteredData(userInfo.bookmarkedOrganizer || []);
        break;
      default:
        setFilteredData([]);
    }
  }, [activeItem, userInfo]);

  // Calculate total pages
  const totalPages = Math.ceil(filteredData.length / itemsPerPage);

  // Get current page data using slice()
  const currentData = filteredData.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handleClick = (item) => {
    setActiveItem(item);
    setCurrentPage(1);
  };

  return (
    <div className="w-full min-h-screen flex flex-row">
      {/* Left SideBar */}
      <ul className="fixed w-[20%] min-h-screen bg-[#343E4F] flex flex-col gap-6 pt-16 text-white px-6">
        <li
          className={`flex items-center gap-4 px-4 cursor-pointer ${
            activeItem === "notes"
              ? "bg-btngreen h-8 rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("notes")}
        >
          <IoBookmark className="w-6 h-6" />
          <p>Notes</p>
        </li>

        <li
          className={`flex items-center gap-4 px-4 cursor-pointer ${
            activeItem === "pyq"
              ? "bg-btngreen h-8 rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("pyq")}
        >
          <IoBookmark className="w-6 h-6" />
          <p>PYQ</p>
        </li>

        <li
          className={`flex items-center gap-4 px-4 cursor-pointer ${
            activeItem === "organizer"
              ? "bg-btngreen h-8 rounded-full text-center"
              : ""
          }`}
          onClick={() => handleClick("organizer")}
        >
          <IoBookmark className="w-6 h-6" />
          <p>Organizer</p>
        </li>
      </ul>

      {/* Main Content */}
      <div className="bg-darkbg w-[80%] p-6 ml-[20%] flex flex-col items-center gap-3">
        <div className="flex items-center border border-white rounded-lg overflow-hidden w-2/3">
          {/* Bookmark Button */}
          <button className="bg-green-500 text-white px-4 py-2 flex items-center">
            <IoBookmark />
            Bookmark
          </button>

          {/* Input Field */}
          <input
            type="text"
            placeholder="Search..."
            className="flex-1 px-4 py-2 outline-none"
          />

          {/* Search Button */}
          <button className="bg-gray-800 text-white px-4 py-2">
            <IoSearchOutline />
          </button>
        </div>

        <span className="h-1 border-b border-gray-300 w-full" />

        <div className="grid grid-cols-3 gap-5 2xl:grid-cols-4">
          {currentData.map((note, index) => (
            <NoteCard
              key={note.id || index}
              id={note.id}
              title={note.topicName || note.year || "Untitled"}
              subject={note.fileType || "Unknown Type"}
              userName={userInfo.name || "Unknown User"}
              noteImage="/notes.webp"
              userImage={userInfo.imageUrl}
              uploadDate={formatDate(note.uploadDateTime)}
              viewLink={`${backendUrl}/${activeItem}/get/${note.id}?option=view`}
              downloadLink={`${backendUrl}/${activeItem}/get/${note.id}?option=download`}
              bookmarkedLink={`${backendUrl}/bookmark/${activeItem}/${note.id}`}
              isInitiallyBookmarked={
                activeItem === "notes"
                  ? userInfo.bookmarkedNotes?.some((n) => n.id === note.id)
                  : activeItem === "pyq"
                  ? userInfo.bookmarkedPyq?.some((n) => n.id === note.id)
                  : userInfo.bookmarkedOrganizer?.some((n) => n.id === note.id)
              }
            />
          ))}
        </div>

        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      </div>
    </div>
  );
};

export default Bookmarked;
