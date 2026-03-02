function updateQR() { 
        let amt = document.getElementById('payAmount').value;
        let qrImg = document.getElementById('qrcode');
        let promptpayID = "0863526387"; // เปลี่ยนเป็นเบอร์ใหม่ที่มึงบอกแล้วเพื่อน!

        if(amt && amt > 0) {
            // ใช้โครงสร้าง URL ของ PromptPay.io แบบใส่ Timestamp ป้องกันการจำค่าเก่า
            // รูปแบบ: https://promptpay.io/เบอร์โทร/จำนวนเงิน.png?v=เวลาปัจจุบัน
            let qrUrl = "https://promptpay.io/" + promptpayID + "/" + amt + ".png?v=" + Date.now();
            
            qrImg.src = qrUrl;
            document.getElementById('qrZone').style.display = 'block';
            console.log("สร้าง QR Code เบอร์: " + promptpayID + " ยอด: ฿" + amt);
        } else {
            qrImg.src = "";
            document.getElementById('qrZone').style.display = 'none';
        }
    }
