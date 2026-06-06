import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import BookCatalogue from "./pages/BookCatalogue";
import IssueBook from "./pages/IssueBook";
import MyBorrows from "./pages/MyBorrows";

import Dashboard from "./pages/admin/Dashboard";
import ManageBooks from "./pages/admin/ManageBooks";
import ManageUsers from "./pages/admin/ManageUsers";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />

          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route
            path="/catalogue"
            element={
              <ProtectedRoute allowedRoles={["MEMBER"]}>
                <BookCatalogue />
              </ProtectedRoute>
            }
          />

          <Route
            path="/issue-book"
            element={
              <ProtectedRoute allowedRoles={["MEMBER"]}>
                <IssueBook />
              </ProtectedRoute>
            }
          />

          <Route
            path="/my-borrows"
            element={
              <ProtectedRoute allowedRoles={["MEMBER"]}>
                <MyBorrows />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/dashboard"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/books"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <ManageBooks />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/users"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <ManageUsers />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;