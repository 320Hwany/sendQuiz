import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Main() {
    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h1 className="card-title text-center mb-4">Send Quiz</h1>
                            <div className="text-center">
                                <Link to="/login" className="btn btn-primary mx-3">
                                    로그인
                                </Link>
                                <Link to="/signup" className="btn btn-secondary mx-3">
                                    회원가입
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Main;
