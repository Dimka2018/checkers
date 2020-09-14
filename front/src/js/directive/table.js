import React from "react";
import '../../css/board.css';
import Chip from "./chip";

class Table extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            boardState: [
                [null, 1, null, 1, null, 1, null, 1],
                [1, null, 1, null, 1, null, 1, null],
                [null, 1, null, 1, null, 1, null, 1],
                [0, null, 0, null, 0, null, 0, null],
                [null, 0, null, 0, null, 0, null, 0],
                [2, null, 2, null, 2, null, 2, null],
                [null, 2, null, 2, null, 2, null, 2],
                [2, null, 2, null, 2, null, 2, null],
            ]
        }
    }

    render() {
        const cells = this.state.boardState.map(row =>
            <div className='line no-gutters'>
                {row.map(cell => <div className={'cell ' + (cell !== null ? 'black' : 'white')} >{!!cell && <Chip color={cell === 1 ? 'white' : 'black'}/>}</div> )}
            </div>);
        return (
            <div className="board">
                {cells}
            </div>
        )
    }
}
export default Table;