function updateQR() { 
        let amt = document.getElementById('payAmount').value;
        let qrImg = document.getElementById('qrcode');
        let promptpayID = "0863112536"; // เบอร์พร้อมเพย์ของย่า

        if(amt > 0) {
            // ใช้โครงสร้าง URL ของ PromptPay.io ที่เสถียรที่สุด
            // รูปแบบ: https://promptpay.io/เบอร์โทร/จำนวนเงิน.png
            let qrUrl = "https://promptpay.io/" + promptpayID + "/" + amt + ".png";
            
            // ป้องกันการจำค่าเก่า (Cache) โดยการเติมเลขสุ่มข้างหลัง
            qrImg.src = qrUrl + "?t=" + Date.now();
            
            document.getElementById('qrZone').style.display = 'block';
            console.log("สร้าง QR Code สำหรับยอด: ฿" + amt);
        } else {
            qrImg.src = "";
            document.getElementById('qrZone').style.display = 'none';
        }
    }
