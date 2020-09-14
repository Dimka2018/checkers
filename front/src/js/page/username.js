import React, {Component} from "react";
import '../../css/username.css';

class Username extends Component {

    render() {
        return (
            <div className='username-background'>
                <div className="username-form">
                    <div className='title'>Enter username</div>
                    <input/>
                    <button>Save</button>
                </div>
            </div>
        );
    }
}

export default Username;