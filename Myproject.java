<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Grandma Massage - Home</title>
    <style>
        body { font-family: 'Sarabun', sans-serif; background-color: #f0f2f5; margin: 0; display: flex; justify-content: center; min-height: 100vh; }
        .app-container { width: 100%; max-width: 500px; padding: 20px; }
        
        /* Login UI */
        #loginOverlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: #2c3e50; display: flex; justify-content: center; align-items: center; z-index: 1000; }
        .login-box { background: white; padding: 40px; border-radius: 20px; width: 300px; text-align: center; }
        
        /* Dashboard UI */
        #dashboard { display: none; text-align: center; margin-top: 30px; }
        .total-card { background: linear-gradient(135deg, #28a745, #218838); color: white; padding: 20px; border-radius: 20px; margin-bottom: 20px; box-shadow: 0 8px 15px rgba(40,167,69,0.2); }
        .menu-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .menu-card { background: white; padding: 25px 10px; border-radius: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); cursor: pointer; transition: 0.3s; }
        .menu-card span { font-size: 35px; display: block; margin-bottom: 5px; }

        /* Sub-App UI */
        .sub-app { display: none; background: white; padding: 25px; border-radius: 20px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; color: #555; font-size: 14px; text-align: left; }
        input, select, button { width: 100%; padding: 12px; margin-top: 8px; border-radius: 10px; border: 1px solid #ddd; box-sizing: border-box; font-size: 16px; }
        button { background-color: #28a745; color: white; border: none; font-weight: bold; cursor: pointer; }
        button:disabled { background-color: #bdc3c7; cursor: not-allowed; }
        .btn-back { background-color: #6c757d; margin-top: 20px; }

        /* Staff & History */
        .staff-status-item { display: flex; justify-content: space-between; align-items: center; padding: 15px; background: #f8f9fa; border-radius: 12px; margin-bottom: 10px; }
        .btn-available { background-color: #28a745; color: black !important; width: auto; padding: 8px 15px; margin-top: 0; }
        .btn-busy { background-color: #dc3545; color: black !important; width: auto; padding: 8px 15px; margin-top: 0; }
        .history-item { border-bottom: 1px solid #eee; padding: 12px 0; text-align: left; font-size: 14px; color: #333; }
        .queue-item { background: #f8f9fa; padding: 15px; border-radius: 12px; border-left: 6px solid #007bff; margin-bottom: 10px; display: flex; justify-content: space-between; align-items: center; text-align: left; }
    </style>
</head>
<body>

<div id="loginOverlay">
    <div class="login-box">
        <h2>🔒 เข้าสู่ระบบ</h2>
        <input type="text" id="userInput" placeholder="ชื่อผู้ใช้">
        <input type="password" id="passInput" placeholder="รหัสผ่าน">
        <button onclick="checkLogin()" style="margin-top: 20px; background-color: #007bff;">ตกลง</button>
        <p id="errorMsg" style="color: red; font-size: 12px; display: none; margin-top: 10px;">ข้อมูลไม่ถูกต้องนะเพื่อน!</p>
    </div>
</div>

<div class="app-container">
    <div id="dashboard">
        <div class="total-card">
            <small>ยอดเงินรวมวันนี้</small>
            <h1 id="dailyTotal">฿0</h1>
        </div>
        <div class="menu-grid">
            <div class="menu-card" onclick="showApp('queueApp')"><span>💆‍♂️</span><strong>จองคิว</strong></div>
            <div class="menu-card" onclick="showApp('paymentApp')"><span>💰</span><strong>จ่ายเงิน</strong></div>
            <div class="menu-card" onclick="showApp('staffApp')"><span>👤</span><strong>สถานะหมอ</strong></div>
            <div class="menu-card" onclick="loadAllHistory()"><span>📋</span><strong>คิวทั้งหมด</strong></div>
        </div>
        <button onclick="location.reload()" class="btn-back" style="width: auto; padding: 8px 20px; margin-top: 30px;">ออกจากระบบ</button>
    </div>

    <div id="queueApp" class="sub-app">
        <h3>💆‍♂️ ลงทะเบียนจองคิว</h3>
        <label>👤 เลือกหมอนวด:</label>
        <select id="staffSelect"></select>
        <label>✨ โปรแกรมนวด:</label>
        <select id="serviceType">
            <option>1. นวดไทย</option><option>2. นวดไทยประคบ</option><option>3. นวดไทยออย</option>
            <option>4. นวดสปา+ขัดผิว</option><option>5. นวดฝ่าเท้า</option><option>6. นวดแก้อาการ</option>
            <option>7. นวดหน้าสมุนไพร</option>
        </select>
        <input type="text" id="customerName" placeholder="ชื่อลูกค้า">
        <input type="number" id="servicePrice" placeholder="ราคา (บาท)">
        <div style="display: flex; gap: 5px;"><input type="date" id="bookingDate"><input type="time" id="bookingTime"></div>
        <button id="submitBtn" onclick="addQueue()" style="margin-top: 15px;">✅ บันทึกคิว</button>
        <hr><div id="queueList"></div>
        <button onclick="showDashboard()" class="btn-back">⬅️ กลับหน้าหลัก</button>
    </div>

    <div id="paymentApp" class="sub-app">
        <h3>💰 ระบบชำระเงิน</h3>
        <input type="number" id="amount" placeholder="ระบุจำนวนเงิน (บาท)">
        <button style="background-color: #007bff;" onclick="generateQR()">💳 สร้าง QR Code</button>
        <div id="paymentArea" class="payment-box" style="display: none; text-align:center; margin-top:15px; background:#e8f5e9; padding:15px; border-radius:15px; border:2px dashed #28a745;">
            <img id="qrcode" src="" alt="QR Code" style="width:200px;">
            <p id="amountText" style="font-size: 20px; font-weight: bold; margin-top: 10px;"></p>
        </div>
        <button onclick="showDashboard()" class="btn-back">⬅️ กลับหน้าหลัก</button>
    </div>

    <div id="staffApp" class="sub-app">
        <h3>👤 สถานะหมอนวด</h3>
        <div id="staffStatusList"></div>
        <button onclick="showDashboard()" class="btn-back">⬅️ กลับหน้าหลัก</button>
    </div>

    <div id="historyApp" class="sub-app">
        <h3>📋 ประวัติคิวทั้งหมด</h3>
        <div id="historyList" style="max-height: 400px; overflow-y: auto;">กำลังโหลด...</div>
        <button onclick="showDashboard()" class="btn-back">⬅️ กลับหน้าหลัก</button>
    </div>
</div>

<script>
    let staffData = [
        { id: 's1', name: 'หมอแดง', isAvailable: true },
        { id: 's2', name: 'หมอเขียว', isAvailable: true },
        { id: 's3', name: 'หมอฟ้า', isAvailable: true }
    ];
    let totalIncome = 0;
    const SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxtL9PniWh6zCFKUqj1Vdcp4z-RfvFdP5obz3MvIomsoa-lCVSgulH7yQo99FJ1xWZThA/exec";
    const PROMPTPAY_ID = "0863526387";

    function checkLogin() {
        if (document.getElementById('userInput').value === "admin" && document.getElementById('passInput').value === "123456") {
            document.getElementById('loginOverlay').style.display = "none";
            showDashboard();
        } else {
            document.getElementById('errorMsg').style.display = "block";
        }
    }

    function showDashboard() {
        document.getElementById('dashboard').style.display = "block";
        document.querySelectorAll('.sub-app').forEach(app => app.style.display = "none");
        document.getElementById('dailyTotal').innerText = `฿${totalIncome}`;
    }

    function showApp(appId) {
        document.getElementById('dashboard').style.display = "none";
        document.getElementById(appId).style.display = "block";
        if (appId === 'queueApp') updateStaffDropdown();
        if (appId === 'staffApp') renderStaffStatus();
    }

    function renderStaffStatus() {
        const list = document.getElementById('staffStatusList');
        list.innerHTML = "";
        staffData.forEach(staff => {
            const div = document.createElement('div');
            div.className = 'staff-status-item';
            div.innerHTML = `<span>${staff.name}</span><button class="${staff.isAvailable ? 'btn-available' : 'btn-busy'}" onclick="toggleStatus('${staff.id}')">${staff.isAvailable ? 'หมอว่าง' : 'หมอไม่ว่าง'}</button>`;
            list.appendChild(div);
        });
    }

    function toggleStatus(id) {
        const staff = staffData.find(s => s.id === id);
        staff.isAvailable = !staff.isAvailable;
        renderStaffStatus();
    }

    function updateStaffDropdown() {
        const select = document.getElementById('staffSelect');
        select.innerHTML = "";
        staffData.forEach(staff => {
            let opt = document.createElement('option');
            opt.value = staff.name;
            opt.text = staff.isAvailable ? staff.name : `${staff.name} (ไม่ว่าง)`;
            opt.disabled = !staff.isAvailable;
            select.appendChild(opt);
        });
    }

    async function addQueue() {
        const name = document.getElementById('customerName').value;
        const price = document.getElementById('servicePrice').value;
        const date = document.getElementById('bookingDate').value;
        const time = document.getElementById('bookingTime').value;
        const staffName = document.getElementById('staffSelect').value;
        const service = document.getElementById('serviceType').value;
        const btn = document.getElementById('submitBtn');

        if (!name || !price || !date || !time) { alert("ข้อมูลไม่ครบ!"); return; }
        btn.disabled = true; btn.innerText = "กำลังบันทึก...";

        try {
            await fetch(SCRIPT_URL, { method: 'POST', mode: 'no-cors', body: JSON.stringify({ name, time, date, staff: staffName, service, price }) });
            const staff = staffData.find(s => s.name === staffName);
            if (staff) staff.isAvailable = false;

            const div = document.createElement('div');
            div.className = 'queue-item';
            div.innerHTML = `<div><strong>${time}</strong> | ${name}<br><small>${service} (฿${price})</small></div><button style="width:auto; padding:5px; background:#28a745;" onclick="finishWork(this, ${price}, '${staffName}')">เสร็จสิ้น</button>`;
            document.getElementById('queueList').prepend(div);
            alert("จองสำเร็จ!");
        } catch (e) { alert("Error!"); }
        finally { btn.disabled = false; btn.innerText = "✅ บันทึกคิว"; }
    }

    function finishWork(btn, price, sName) {
        totalIncome += parseInt(price);
        const staff = staffData.find(s => s.name === sName);
        if (staff) staff.isAvailable = true;
        btn.parentElement.remove();
        document.getElementById('dailyTotal').innerText = `฿${totalIncome}`;
    }

    async function loadAllHistory() {
        showApp('historyApp');
        const list = document.getElementById('historyList');
        list.innerHTML = "กำลังดึงข้อมูล...";
        try {
            const res = await fetch(SCRIPT_URL);
            const data = await res.json();
            list.innerHTML = "";
            data.reverse().forEach(item => {
                const div = document.createElement('div');
                div.className = 'history-item';
                div.innerHTML = `📅 ${item.date} | 👤 ${item.name}<br>✨ ${item.service} | 👨‍⚕️ ${item.staff} (฿${item.price})`;
                list.appendChild(div);
            });
        } catch (e) { list.innerHTML = "โหลดข้อมูลไม่สำเร็จ"; }
    }

    function generateQR() {
        const amt = document.getElementById('amount').value;
        if (!amt) return;
        document.getElementById('qrcode').src = `https://promptpay.io/${PROMPTPAY_ID}/${amt}.png`;
        document.getElementById('amountText').innerText = `฿${amt}`;
        document.getElementById('paymentArea').style.display = "block";
    }
</script>
</body>
</html>