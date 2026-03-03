// ตัวอย่างระบบเลือกหมอ
function loginAsDoctor(doctorName) {
    localStorage.setItem('currentDoctor', doctorName); // จำไว้ว่าตอนนี้ใครใช้เครื่องนี้
    renderDoctorQueue(); // ไปหน้าคิวของหมอคนนั้น
}

// ฟังก์ชันกรองคิว (Show only my jobs)
function renderDoctorQueue() {
    const myName = localStorage.getItem('currentDoctor');
    const myJobs = allJobs.filter(job => job.doctorName === myName);
    
    // เอา myJobs ไปโชว์ในหน้าจอหมอ
    console.log("คิวของ " + myName + " คือ: ", myJobs);
}
