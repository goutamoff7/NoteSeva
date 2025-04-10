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
}) {
  const [pdfPreview, setPdfPreview] = useState(null);

  useEffect(() => {
    const generatePdfPreview = async () => {
      try {
        const loadingTask = pdfjsLib.getDocument(noteImage);
        const pdf = await loadingTask.promise;

        const page = await pdf.getPage(1);
        const viewport = page.getViewport({ scale: 1.5 });

        const canvas = document.createElement("canvas");
        const context = canvas.getContext("2d");
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        const renderContext = {
          canvasContext: context,
          viewport: viewport,
        };
        await page.render(renderContext).promise;

        const imageDataUrl = canvas.toDataURL("image/png");
        setPdfPreview(imageDataUrl);
      } catch (error) {
        console.error("Error generating PDF preview:", error);
        setPdfPreview("/fallback-image.jpg");
      }
    };

    if (noteImage && noteImage.endsWith(".pdf")) {
      generatePdfPreview();
    } else {
      setPdfPreview(noteImage);
    }
  }, [noteImage]);

  const handleViewClick = () => {
    window.open(noteImage, "_blank");
  };

  return (
    <div className="w-64 rounded-2xl shadow-lg overflow-hidden border">
      <div className="p-4">
        <div className="flex justify-between">
          <div className="font-bold text-lg text-white">{title}</div>
          <p className="text-white cursor-pointer">
            <BsThreeDotsVertical />
          </p>
        </div>
        <div className="text-gray-500 text-sm mb-2">{subject}</div>

        <div className="bg-gray-100 rounded-md overflow-hidden h-32 flex items-center justify-center">
          <img
            src={pdfPreview || "image.png"}
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
            src={userImage}
            alt="Profile"
            className="w-8 h-8 rounded-full object-cover"
          />
          <div>
            <div className="font-semibold text-sm text-white">{userName}</div>
            <div className="text-gray-400 text-xs">26 January 2021</div>
          </div>
        </div>
      </div>
    </div>
  );
}
