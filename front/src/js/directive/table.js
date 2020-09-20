import React from "react";
import '../../css/board.css';
import Chip from "./chip";

class Table extends React.Component {

    constructor(props) {
        super(props);
        this.board = [
            [null, 1, null, 1, null, 1, null, 1],
            [1, null, 1, null, 1, null, 1, null],
            [null, 1, null, 1, null, 1, null, 1],
            [0, null, 0, null, 0, null, 0, null],
            [null, 0, null, 0, null, 0, null, 0],
            [2, null, 2, null, 2, null, 2, null],
            [null, 2, null, 2, null, 2, null, 2],
            [2, null, 2, null, 2, null, 2, null]
        ];
        this.state = {
            boardState: this.board
        };
        this.isBlocked = false;
    }

    onBoardChanged() {
        console.log(this.state.boardState);
        this.props.onBoardChanged(this.board);
    }

    setBoardState(boardState) {
        this.setState({boardState: boardState})
    }

    block() {
        this.isBlocked = true;
    }

    unBlock() {
        this.isBlocked = false;
    }

    handleClick(row, col) {
        if (!this.isBlocked) {
            if (this.board[row][col] === 2) {
                this.activeChip = {
                    row: row,
                    col: col
                }
            } else if (this.canAttack(row, col)) {
                let removeRow = this.activeChip.row - row > 0 ? row + 1 : row - 1;
                let removeCol = this.activeChip.col - col > 0 ? col + 1 : col - 1;
                this.remove(removeRow, removeCol);
                this.move(row, col);
                if (!(this.canAttack(this.activeChip.row + 2, this.activeChip.col + 2) ||
                    this.canAttack(this.activeChip.row + 2, this.activeChip.col - 2) ||
                    this.canAttack(this.activeChip.row  -2, this.activeChip.col + 2) ||
                    this.canAttack(this.activeChip.row - 2, this.activeChip.col - 2))) {
                    this.activeChip = undefined;
                    this.onBoardChanged();
                }
            } else if (this.canMove(row, col)) {
                this.move(row, col);
                this.activeChip = undefined;
                this.onBoardChanged();
            }
        }
    }

    canMove(row, col) {
        let rowDist = this.activeChip.row - row;
        let colDist = this.activeChip.col - col;
        return (rowDist === 1 && Math.abs(colDist) === 1 && this.state.boardState[row][col] === 0);
    }

    remove(row, col) {
        let board = [...this.state.boardState];
        board[row][col] = 0;
        this.setState({boardState: board});
    }

    canAttack(row, col) {
        return  this.canAttackForward(row, col) || this.canAttackBack(row, col);
    }

    canAttackForward(row, col) {
        let rowDist = this.activeChip.row - row;
        let colDist = this.activeChip.col - col;
        return (rowDist === 2 && Math.abs(colDist) === 2 &&
            this.state.boardState[row][col] === 0 &&
            (this.state.boardState[row + 1][col - 1] === 1 || this.state.boardState[row + 1][col + 1] === 1))
    }

    canAttackBack(row, col) {
        let rowDist = this.activeChip.row - row;
        let colDist = this.activeChip.col - col;
        return (rowDist === -2 && Math.abs(colDist) === 2 &&
            this.state.boardState[row][col] === 0 &&
            (this.state.boardState[row - 1][col - 1] === 1 || this.state.boardState[row - 1][col + 1] === 1))
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
        this.activeChip.row = row;
        this.activeChip.col = col;
        this.setState({boardState: newBoard});
        this.board = newBoard;
        return newBoard;
    }

    render() {
        const cells = this.state.boardState.map((row, rowIndex) =>
            <div className='line no-gutters'>
                {row.map((cell, colIndex) => <div className={'cell ' + (cell !== null ? 'black' : 'white')} onClick={() => this.handleClick(rowIndex, colIndex)} >
                    {!!cell && <Chip color={cell === 1 ? 'white' : 'black'} id={rowIndex + '' + colIndex}/>}
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