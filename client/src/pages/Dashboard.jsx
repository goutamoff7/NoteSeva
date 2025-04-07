import React from 'react'
import { useAppContext } from "../context/AppContext";

const Dashboard = () => {

  const {apiClient,isAuthenticated} = useAppContext()
  

  return (
    isAuthenticated && (
      <div>Dashboard</div>
    )
  )
}

export default Dashboard