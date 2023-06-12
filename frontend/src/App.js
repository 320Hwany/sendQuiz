import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './component/member/Signup';
import Login from "./component/member/Login";
import Home from "./component/Home";
import Main from "./component/Main";
import SignupSuccess from "./component/member/SignupSuccess";
import Setting from "./component/member/Setting";
import Quiz from "./component/quiz/Quiz";
import Manage from "./component/member/Manage";
import Delete from "./component/member/Delete";
import Update from "./component/member/Update";
import FindPassword from "./component/member/FindPassword";
import Suggestions from "./component/Suggestions";
import QuizUpdate from "./component/quiz/QuizUpdate";
import Quizzes from "./component/quiz/Quizzes";

function App() {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/main" element={<Main />} />
                    <Route path="/setting" element={<Setting />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/signup/success" element={<SignupSuccess />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/hwany/quiz" element={<Quiz />} />
                    <Route path="/hwany/quiz/update" element={<QuizUpdate />} />
                    <Route path="/manage" element={<Manage />} />
                    <Route path="/delete" element={<Delete />} />
                    <Route path="/update" element={<Update />} />
                    <Route path="/find/password" element={<FindPassword />} />
                    <Route path="/suggestions" element={<Suggestions />} />
                    <Route path="/quizzes" element={<Quizzes />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
