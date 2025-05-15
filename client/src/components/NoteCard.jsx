import React, { useState, useRef } from "react";
import { IoBookmark, IoBookmarkOutline } from "react-icons/io5";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import { BsThreeDotsVertical } from "react-icons/bs";
import { IoClose } from "react-icons/io5";

export default function NoteCard({
  id,
  title,
  subject,
  userImage,
  noteImage,
  userName,
  uploadDate,
  year,
  downloadLink,
  viewLink
}) {
  const [menuOpen, setMenuOpen] = useState(false);
  const [isLiked, setIsLiked] = useState(false);
  const [isBookmarked, setIsBookmarked] = useState(false);

  const likeSoundRef = useRef(null);
  const bookmarkSoundRef = useRef(null);

  const handleViewClick = () => {
    window.open(viewLink, "_blank");
  };

  const handleDownload = () => {
    const a = document.createElement("a");
    a.href = downloadLink;
    a.download = `${title || "note"}.pdf`;
    a.click();
  };

  const toggleLike = () => {
    if (!isLiked && likeSoundRef.current) {
      likeSoundRef.current.currentTime = 0;
      likeSoundRef.current.play();
    }
    setIsLiked(!isLiked);
  };

  const toggleBookmark = () => {
    if (!isBookmarked && bookmarkSoundRef.current) {
      bookmarkSoundRef.current.currentTime = 0;
      bookmarkSoundRef.current.play();
    }
    setIsBookmarked(!isBookmarked);
  };

  return (
    <div className="w-64 rounded-2xl shadow-lg overflow-hidden border">

      {/* Sound Effects */}
      <audio ref={likeSoundRef} src="/like-btn.mp3" preload="auto" />
      <audio ref={bookmarkSoundRef} src="/bookmarked-btn.mp3" preload="auto" />

      <div className="p-4">
        <div className="flex justify-between">
          <div className="font-bold text-lg text-white">{title || year}</div>
          <div className="relative">
            <button
              className="text-white cursor-pointer"
              onClick={() => setMenuOpen(!menuOpen)}
            >
              {menuOpen ? <IoClose size={20} /> : <BsThreeDotsVertical size={20} />}
            </button>

            {menuOpen && (
              <div className="absolute right-0 mt-2 w-32 bg-white rounded-md shadow-lg z-1">
                <button
                  className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                  onClick={handleDownload}
                >
                  Download
                </button>
              </div>
            )}
          </div>
        </div>

        <div className="text-gray-500 text-sm mb-2">{subject}</div>

        <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex items-center justify-center">
          <img
            src={noteImage || "/notecard.jpg" || "/notes.webp"}
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
            <button onClick={toggleLike}>
              {isLiked ? (
                <FaHeart className="w-5 h-5 text-red-500" />
              ) : (
                <FaRegHeart className="w-5 h-5 text-gray-400" />
              )}
            </button>
            <span className="text-gray-500">{isLiked ? "3.3k" : "3.2k"}</span>
            <button onClick={toggleBookmark}>
              {isBookmarked ? (
                <IoBookmark className="w-5 h-5 text-blue-600" />
              ) : (
                <IoBookmarkOutline className="w-5 h-5 text-gray-500" />
              )}
            </button>
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
