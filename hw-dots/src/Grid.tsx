/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    parsedText: string[][];    // the text of the texting box
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {

        super(props);

        this.state = {

            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }


    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
        this.drawLine();
    }

    componentDidUpdate() {
        this.redraw()
        this.drawLine();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        ctx.clearRect(0, 0, this.props.width, this.props.height);

        // Once the image is done loading, it'll be saved inside our state, and we can draw it.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        // A hardcoded 4x4 grid. Probably not going to work when we change the grid size...
        let result:[number,number][] = [];
        let spaceWidth: number = this.props.width / (this.props.size+1);
        let spaceHeight: number = this.props.height / (this.props.size+1);
        for(let i = 1; i <= this.props.size; i++){
            for(let j = 1; j <= this.props.size;j++){
                result.push([spaceHeight*i,spaceWidth*j]);

            }
        }
        return result;

    };

    drawCircle = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };
    drawLine = () =>{
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }
        ctx.beginPath();
        for(let i = 0 ; i < this.props.parsedText.length ; i ++){
            if(this.props.parsedText[i].length!=this.props.size){
                window.alert("There was an error with some of your line input. \nFor reference, the correct form "+
                "for each line is: x1,y1 x2,y2 color\n\n")
                if(this.props.parsedText[i].length<this.props.size){
                    window.alert("Line"+(i+1)+": Missing a portion of the line, or missing a space.")
                }else{
                    window.alert("Line"+(i+1)+": Extra portion of the line, or an extra space.")
                }
            }
            this.drawOneLine(ctx,this.props.parsedText[i]);
        }
        ctx.stroke();
        //TODO:color
        //TODO:when and how to call this method
    }
    drawOneLine = (ctx: CanvasRenderingContext2D, line:string[]) =>{
        let start = line[0].split(",");
        let startX = parseFloat(start[0]);
        let startY = parseFloat(start[1]);
        let end = line[1].split(",");
        let endX = parseFloat(end[0]);
        let endY = parseFloat(end[1]);
        let color = line[2];
        ctx.moveTo(startX,startY);
        ctx.lineTo(endX,endY);
    }


    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
