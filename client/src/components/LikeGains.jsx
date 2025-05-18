const LikeGains = ({ userUploads }) => {
  // Extracting different upload types
  const {
    notesDTOList = [],
    organizerDTOList = [],
    pyqDTOList = [],
  } = userUploads || {};

  // Calculating total likes for each category
  const totalNotesLikes = notesDTOList.reduce(
    (sum, note) => sum + (note.likeCount || 0),
    0
  );
  const totalOrganizerLikes = organizerDTOList.reduce(
    (sum, organizer) => sum + (organizer.likeCount || 0),
    0
  );
  const totalPyqLikes = pyqDTOList.reduce(
    (sum, pyq) => sum + (pyq.likeCount || 0),
    0
  );

  const totalLike = totalNotesLikes + totalOrganizerLikes + totalPyqLikes;

  console.log("Total Notes Likes:", totalNotesLikes);
  console.log("Total Organizer Likes:", totalOrganizerLikes);
  console.log("Total PYQ Likes:", totalPyqLikes);

  return (
    <div className="bg-[#1f2937] p-2 rounded-2xl w-54 h-52  shadow-md text-white flex flex-col justify-between items-center border border-gray-100">
      <div className="flex items-center flex-col mb-2">
        <div className="flex items-center gap-2 w-full">
          <span className="text-2xl">🤍</span>
          <h2 className="text-2xl font-semibold">Likes Gained</h2>
        </div>
        <p className="text-gray-400 text-sm font-semibold">Total {totalLike}</p>
        <div className="flex flex-col justify-between items-start w-full px-2 -space-y-2">
          <div className="flex items-center gap-x-4">
            <p className="flex items-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="98.47"
                height="120"
                viewBox="0 0 20 26"
                className="size-14 text-white py-4"
              >
                <path
                  fill="currentColor"
                  d="M13.41 0H2C.9 0 0 .9 0 2v22c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6.58zm2.09 20h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m0-3h-11c-.28 0-.5-.22-.5-.5s.22-.5.5-.5h11c.28 0 .5.22.5.5s-.22.5-.5.5m.5-3.444c0 .248-.22.444-.5.444h-11c-.28 0-.5-.196-.5-.444v-3.11c0-.25.22-.446.5-.446h11c.28 0 .5.196.5.444zM15 7a2 2 0 0 1-2-2V1l6 6z"
                />
              </svg>
              <span>Notes</span>
            </p>
            <p>{totalNotesLikes}</p>
          </div>
          <div className="flex items-center gap-x-4">
            <p className="flex items-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="128"
                height="128"
                viewBox="0 0 32 32"
                className="size-14 text-white py-4"
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
              <span>PYQ</span>
            </p>
            <p>{totalPyqLikes}</p>
          </div>
          <div className="flex items-center gap-x-4">
            <p className="flex items-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="128"
                height="128"
                viewBox="0 0 24 24"
                className="size-14 text-white py-4"
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
              <span>Organizer</span>
            </p>
            <p>{totalOrganizerLikes}</p>
          </div>
        </div>
      </div>
      <div className="w-full h-1/2"></div>
    </div>
  );
};

export default LikeGains;
