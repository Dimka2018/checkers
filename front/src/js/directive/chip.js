import React from "react";
import '../../css/chip.css';

class Chip extends React.Component{

    onDragStart(event) {
        console.log(event);
    }

    render() {
        const mainColor = this.props.color === 'white' ? "white" : "black";
        const borderColor = this.props.color === 'white' ? "b-black" : "b-white";
        return (
            <div onMouseDown={this.onDragStart} className={`${mainColor} ${borderColor} rounded-circle chip b-white flex-center`}>
                <div className={`${mainColor} ${borderColor} rounded-circle flex-center w-75 h-75`}>
                    <div className={`${mainColor} ${borderColor} rounded-circle w-75 h-75`}/>
                </div>
            </div>
        )
    }
}

export default Chip;