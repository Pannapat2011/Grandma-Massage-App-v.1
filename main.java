// --- ฟังก์ชันสร้าง QR Code ที่ธนาคารสแกนได้จริง ---
    function updateQR() { 
        let a = document.getElementById('payAmount').value;
        let qrImg = document.getElementById('qrcode');
        let promptpayID = "0863112536"; // เบอร์ที่มึงให้ไว้

        if(a > 0) {
            // ใช้ PromptPay.io API เพื่อให้แอปธนาคารสแกนติด
            qrImg.src = `https://promptpay.io/${promptpayID}/${a}.png`;
            document.getElementById('qrZone').style.display = 'block';
        } else {
            qrImg.src = "";
            document.getElementById('qrZone').style.display = 'none';
        }
    }
