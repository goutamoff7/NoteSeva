import React, { useState, useEffect } from "react";
import { useAppContext } from "../context/AppContext";
import { toast } from "react-toastify";


const MyProfile = () => {
  const [isEdit, setIsEdit] = useState(false);
  const [image, setImage] = useState(null);
  const { apiClient, isAuthenticated, userInfo ,userData } = useAppContext();

  const [editableUser, setEditableUser] = useState({
    name: "",
    collegeName: "",
    gender: "",
    linkedInUrl: "",
    gitHubUrl: "",
    othersUrl: ""
  });

  // Update editableUser when userInfo becomes available
  useEffect(() => {
    if (userInfo) {
      setEditableUser({
        name: userInfo.name || "",
        collegeName: userInfo.collegeName || "",
        gender: userInfo.gender || "",
        linkedInUrl: userInfo.linkedInUrl || "",
        gitHubUrl: userInfo.gitHubUrl || "",
        othersUrl: userInfo.othersUrl || ""
      });
    }
  }, [userInfo]);

  const handleSave = async () => {
    try {
      const res = await apiClient.put("/user/update-user-details", editableUser);
      console.log("Updated successfully:", res.data);
      toast.success("Profile Updated Successfully")
      await userData();
      setIsEdit(false);
    } catch (error) {
      toast.error(error)
      console.error("Failed to update profile:", error);
    }
  };

  // If not authenticated or userInfo is still loading
  if (!isAuthenticated || !userInfo) {
    return <p className="text-white px-10 py-10">Loading profile...</p>;
  }

  return (
    <div className="w-full min-h-screen flex flex-col gap-2 text-sm px-10 py-10 bg-darkbg">
      {/* Profile image */}
      {isEdit ? (
        <label htmlFor="image">
          <div className="inline-block relative cursor-pointer">
            <img
              className="w-36 h-36 rounded-full opacity-30"
              src={image ? URL.createObjectURL(image) : userInfo.imageUrl}
              alt="Profile"
            />
            <img
              className="w-10 absolute bottom-12 right-12"
              src={image ? "" : "./upload_icon.png"}
              alt="Upload"
            />
          </div>
          <input
            onChange={(e) => setImage(e.target.files[0])}
            type="file"
            id="image"
            hidden
          />
        </label>
      ) : (
        <img
          className="w-36 h-36 rounded-full"
          src={userInfo.imageUrl || "./upload_area.png"}
          alt="Profile"
        />
      )}

      {/* Profile name */}
      {isEdit ? (
        <input
          className="bg-gray-400 p-1 rounded-md text-3xl font-medium max-w-60 mt-4"
          type="text"
          value={editableUser.name}
          onChange={(e) => setEditableUser((prev) => ({ ...prev, name: e.target.value }))}
        />
      ) : (
        <p className="font-medium text-3xl text-whitee mt-4">{userInfo.name}</p>
      )}

      <hr className="bg-zinc-400 h-[1px] border-none" />

      <div className="grid grid-cols-[1fr_3fr] gap-y-3 pt-10 text-whitee">
        <p className="font-medium">Email id:</p>
        <p className="text-blue-400">{userInfo.email}</p>

        <p className="font-medium">Username:</p>
        <p className="text-blue-400">{userInfo.username}</p>

        <p className="font-medium">Gender:</p>
        {isEdit ? (
          <select
            className="max-w-24 bg-gray-400 text-black p-1 rounded-md"
            onChange={(e) => setEditableUser((prev) => ({ ...prev, gender: e.target.value }))}
            value={editableUser.gender}
          >
            <option value="">Select</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        ) : (
          <p className="text-gray-400">{userInfo.gender ?? "null"}</p>
        )}

        <p className="font-medium">College Name:</p>
        {isEdit ? (
          <input
            className="bg-gray-400 text-black p-1 max-w-72 rounded-md"
            type="text"
            value={editableUser.collegeName}
            onChange={(e) => setEditableUser((prev) => ({ ...prev, collegeName: e.target.value }))}
          />
        ) : (
          <p className="text-gray-400">{userInfo.collegeName ?? "null"}</p>
        )}

        <p className="font-medium">GitHub:</p>
        {isEdit ? (
          <input
            className="bg-gray-400 text-black p-1 max-w-72 rounded-md"
            type="url"
            value={editableUser.gitHubUrl}
            onChange={(e) => setEditableUser((prev) => ({ ...prev, gitHubUrl: e.target.value }))}
          />
        ) : userInfo.gitHubUrl ? (
          <a
            href={userInfo.gitHubUrl}
            className="hover:underline text-gray-400"
            target="_blank"
            rel="noopener noreferrer"
          >
            {userInfo.gitHubUrl}
          </a>
        ) : (
          <span className="text-gray-400">null</span>
        )}

        <p className="font-medium">LinkedIn:</p>
        {isEdit ? (
          <input
            className="bg-gray-400 text-black p-1 max-w-72 rounded-md"
            type="url"
            value={editableUser.linkedInUrl}
            onChange={(e) => setEditableUser((prev) => ({ ...prev, linkedInUrl: e.target.value }))}
          />
        ) : userInfo.linkedInUrl ? (
          <a
            href={userInfo.linkedInUrl}
            className="hover:underline text-gray-400"
            target="_blank"
            rel="noopener noreferrer"
          >
            {userInfo.linkedInUrl}
          </a>
        ) : (
          <span className="text-gray-400">null</span>
        )}

        <p className="font-medium">Other:</p>
        {isEdit ? (
          <input
            className="bg-gray-400 text-black p-1 max-w-72 rounded-md"
            type="url"
            value={editableUser.othersUrl}
            onChange={(e) => setEditableUser((prev) => ({ ...prev, othersUrl: e.target.value }))}
          />
        ) : userInfo.otherUrl ? (
          <a
            href={userInfo.otherUrl}
            className="hover:underline text-gray-400"
            target="_blank"
            rel="noopener noreferrer"
          >
            {userInfo.otherUrl}
          </a>
        ) : (
          <span className="text-gray-400">null</span>
        )}
      </div>

      {/* Save and edit buttons */}
      <div className="mt-10">
        {isEdit ? (
          <button
            className="border px-8 py-2 rounded-full text-white hover:bg-whitee hover:text-black transition-all duration-300"
            onClick={handleSave}
          >
            Save Information
          </button>
        ) : (
          <button
            className="border px-8 py-2 rounded-full text-white hover:bg-whitee hover:text-black transition-all duration-300"
            onClick={() => setIsEdit(true)}
          >
            Edit
          </button>
        )}
      </div>
    </div>
  );
};

export default MyProfile;
