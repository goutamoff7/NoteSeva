import React, { useState, useRef } from "react";
import { IoBookmark, IoBookmarkOutline } from "react-icons/io5";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import { BsThreeDotsVertical } from "react-icons/bs";
import { IoClose } from "react-icons/io5";
import { useAppContext } from "../context/AppContext";
import { toast } from "react-toastify";

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
  viewLink,
  bookmarkedLink,
  likeLink,
  isInitiallyBookmarked,
  isInitiallyLiked,
  totalLike,
  likedUsers,
  noteFetch,
  docType,
}) {
  const [menuOpen, setMenuOpen] = useState(false);
  const [isLiked, setIsLiked] = useState(isInitiallyLiked);
  const [likeCount, setLikeCount] = useState(totalLike);
  const [isBookmarked, setIsBookmarked] = useState(isInitiallyBookmarked);
  const [loading, setLoading] = useState(false);
  const { apiClient, userBookmarkedDocs } = useAppContext();
  const [likePopupOpen, setLikePopupOpen] = useState(false);

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

  const toggleLike = async () => {
    try {
      setLoading(true);

      const newLikeStatus = !isLiked;
      setIsLiked(newLikeStatus);
      setLikeCount(newLikeStatus ? likeCount + 1 : likeCount - 1);

      if (newLikeStatus) {
        await apiClient.post(likeLink);

        if (likeSoundRef.current) {
          likeSoundRef.current.currentTime = 0;
          likeSoundRef.current.play();
        }
      } else {
        await apiClient.delete(likeLink);
      }

      // Refresh the notes list
      if (noteFetch) {
        noteFetch();
        userBookmarkedDocs();
      }
    } catch (error) {
      console.error("Like request failed:", error);
      toast.error("Failed to update like status. Please try again.");

      setIsLiked(!isLiked);
      setLikeCount(isLiked ? likeCount + 1 : likeCount - 1);
    } finally {
      setLoading(false);
    }
  };

  const toggleBookmark = async () => {
    try {
      setLoading(true);

      if (isBookmarked) {
        await apiClient.delete(bookmarkedLink);
        setIsBookmarked(false);
      } else {
        await apiClient.post(bookmarkedLink);
        setIsBookmarked(true);

        if (bookmarkSoundRef.current) {
          bookmarkSoundRef.current.currentTime = 0;
          bookmarkSoundRef.current.play();
        }
      }
      if (userBookmarkedDocs) {
        userBookmarkedDocs();
      }
    } catch (error) {
      console.error("Bookmark request failed:", error);
      toast.error("Failed to update bookmark status. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-64 rounded-2xl shadow-lg overflow-hidden border relative">
      {/* Sound Effects */}
      <audio ref={likeSoundRef} src="/like-btn.mp3" preload="preload" />
      <audio
        ref={bookmarkSoundRef}
        src="/bookmarked-btn.mp3"
        preload="preload"
      />

      <div className="p-4">
        <div className="flex justify-between">
          <div className="font-bold text-lg text-white">{title || year}</div>
          <div className="relative">
            <button
              className="text-white cursor-pointer"
              onClick={() => setMenuOpen(!menuOpen)}
            >
              {menuOpen ? (
                <IoClose size={20} />
              ) : (
                <BsThreeDotsVertical size={20} />
              )}
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
          {docType === "notes" ? (
            <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex flex-col items-center justify-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="98.47"
                height="120"
                viewBox="0 0 20 26"
                className="h-full w-full text-black py-4"
              >
                <path
                  fill="currentColor"
                  d="M13.41 0H2C.9 0 0 .9 0 2v22c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6.58zm2.09 20h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m0-3h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m.5-3.444c0 .248-.22.444-.5.444h-11c-.28 0-.5-.196-.5-.444v-3.11c0-.25.22-.446.5-.446h11c.28 0 .5.196.5.444zM15 7a2 2 0 0 1-2-2V1l6 6z"
                />
              </svg>
              <p className="text-base font-bold mb-4">Notes</p>
            </div>
          ) : docType === "pyq" ? (
            <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex flex-col items-center justify-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="128"
                height="128"
                viewBox="0 0 32 32"
                className="h-full w-full text-black py-4"
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
              <p className="text-base font-bold mb-4">PYQ</p>
            </div>
          ) : docType === "organizer" ? (
            <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex flex-col items-center justify-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="128"
                height="128"
                viewBox="0 0 24 24"
                className="h-full w-full text-black py-4"
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
              <p className="text-base font-bold mb-4">Organizer</p>
            </div>
          ) : (
            <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex flex-col items-center justify-center">
              <p className="text-base font-bold mb-4">Unknown Type</p>
            </div>
          )}
        </div>

        <div className="flex justify-between items-center mt-4">
          <button
            onClick={handleViewClick}
            className="bg-green-500 text-white hover:bg-green-600 px-4 py-2 rounded-lg"
          >
            View
          </button>

          <div className="flex items-center space-x-2">
            <button onClick={toggleLike} disabled={loading}>
              {isLiked ? (
                <FaHeart className="w-5 h-5 text-red-500" />
              ) : (
                <FaRegHeart className="w-5 h-5 text-gray-400" />
              )}
            </button>
            <span
              className="text-gray-500 cursor-pointer"
              onClick={() => setLikePopupOpen(!likePopupOpen)}
            >
              {likeCount}
            </span>

            <button onClick={toggleBookmark} disabled={loading}>
              {isBookmarked ? (
                <IoBookmark className="w-5 h-5 text-white" />
              ) : (
                <IoBookmarkOutline className="w-5 h-5 text-gray-500" />
              )}
            </button>

            {likePopupOpen && (
              <div className="absolute right-0 bottom-0 mt-2 bg-transparent rounded-md shadow-lg z-20 p-2 max-h-60 overflow-y-auto">
                {likedUsers.length > 0 ? (
                  likedUsers.map((user, index) => (
                    <div
                      key={index}
                      className="text-gray-700 px-2 py-1 hover:bg-gray-100 rounded"
                    >
                      <img
                        className="size-5 rounded-full"
                        src={user.imageUrl}
                        alt=""
                      />
                    </div>
                  ))
                ) : (
                  <div className="text-gray-500 px-2 py-1">No likes yet</div>
                )}
              </div>
            )}
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
