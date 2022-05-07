package com.example.studentareaproject

class Schedule {
    var id : Int = 0
    var discipline : String = ""
    var class_day : String = ""
    var start_at : String = ""
    var end_at : String = ""
    var student_id : Int = 0

    constructor(discipline:String, class_day:String, start_at:String, end_at:String, student_id:Int){
        this.discipline = discipline
        this.class_day = class_day
        this.start_at = start_at
        this.end_at = end_at
        this.student_id = student_id
    }
    constructor(){
    }
}