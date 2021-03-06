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
import "./MapView.css";

interface MapViewProps{
    path: [number,number][];
}
interface MapViewState {
    backgroundImage: HTMLImageElement | null;
}


class MapView extends Component<MapViewProps, MapViewState> {


    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapViewProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    componentDidUpdate() {
        this.drawBackgroundImage();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    /**
     * draw the background image and the shortest path from start to end
     */
    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        for(let i = 0 ; i < this.props.path.length-1;i++){
            ctx.strokeStyle = 'red';
            ctx.lineWidth= 13;
            this.drawOneLine(ctx,this.props.path[i],this.props.path[i+1]);
        }
    }

    /**
     * draw one line from starting coordinate to ending coordinate
     * @param ctx
     * @param start coordinate
     * @param end coordinate
     */
    drawOneLine = (ctx: CanvasRenderingContext2D,start:[number,number],end:[number,number])=>{
        ctx.beginPath();
        ctx.moveTo(start[0],start[1]);
        ctx.lineTo(end[0],end[1]);
        ctx.stroke();
    }

    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default MapView;
