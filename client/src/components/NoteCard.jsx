import React, { useState, useRef } from "react";
import axios from "axios";
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

      // Revert optimistic update in case of error
      setIsLiked(!isLiked);
      setLikeCount(isLiked ? likeCount + 1 : likeCount - 1);
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
                      <img className="size-5 rounded-full" src={user.imageUrl} alt="" />
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
