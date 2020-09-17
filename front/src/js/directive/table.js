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

    handleClick(row, col) {
        if (this.state.boardState[row][col] === 2) {
            this.activeChip = {
                row: row,
                col: col
            }
        } else if (this.canAttack(row, col)) {
            let removeCol = this.activeChip.col - col > 0 ? col + 1 : col - 1;
            this.remove(row + 1, removeCol);
            this.move(row, col);
        } else if (this.canMove(row, col)) {
            this.move(row, col);
        }
    }

    canMove(row, col) {
        let rowDist = this.activeChip.row - row;
        let colDist = this.activeChip.col - col;
        console.log(rowDist);
        console.log(colDist);
        return (rowDist === 1 && Math.abs(colDist) === 1 && this.state.boardState[row][col] === 0);
    }

    remove(row, col) {
        let board = [...this.state.boardState];
        board[row][col] = 0;
        this.setState({boardState: board});
    }

    canAttack(row, col) {
        let rowDist = this.activeChip.row - row;
        let colDist = this.activeChip.col - col;
        return (rowDist === 2 && Math.abs(colDist) === 2 &&
            this.state.boardState[row][col] === 0 &&
            (this.state.boardState[row + 1][col - 1] === 1 || this.state.boardState[row + 1][col + 1] === 1))
    }

    move(row, col) {
        let newBoard = this.state.boardState.map((r, rowIndex) => {
            return r.map((c, colIndex) => {
                if (rowIndex === this.activeChip.row && colIndex === this.activeChip.col) {
                    return 0;
                } else if (rowIndex === row && colIndex === col) {
                    return 2;
                }
                return c;
            })
        });
        console.log(newBoard);
        this.setState({boardState: newBoard});
        this.activeChip = undefined;
    }

    render() {
        const cells = this.state.boardState.map((row, rowIndex) =>
            <div className='line no-gutters'>
                {row.map((cell, colIndex) => <div className={'cell ' + (cell !== null ? 'black' : 'white')} onClick={() => this.handleClick(rowIndex, colIndex)} >
                    {!!cell && <Chip color={cell === 1 ? 'white' : 'black'} />}
                </div> )}
            </div>);
        return (
            <div className="board">
                {cells}
            </div>
        )
    }
}
export default Table;