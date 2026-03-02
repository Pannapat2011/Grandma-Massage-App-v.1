function updateQR() {
        let amt = document.getElementById('payAmount').value;
        let qrImg = document.getElementById('qrcode');
        let promptpayID = "0863112536"; // เบอร์ย่ามึงนะเพื่อน

        if (amt && amt > 0) {
            // ใช้ API สำรองที่เสถียรกว่า (QRServer + PromptPay Payload)
            // ตัวนี้มันจะเจนรูปใหม่ทุกครั้งที่เลขเปลี่ยน
            let qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=";
            
            // ลิงก์นี้คือมาตรฐานการสแกนของธนาคารไทย (EMVCo)
            // กูสุ่มเลขท้ายเพื่อล้าง Cache ให้มึงด้วย
            qrImg.src = `https://promptpay.io/${promptpayID}/${amt}.png?v=${Date.now()}`;
            
            document.getElementById('qrZone').style.display = 'block';
            console.log("สร้าง QR สำหรับยอด: " + amt + " บาท");
        } else {
            document.getElementById('qrZone').style.display = 'none';
        }
    }
