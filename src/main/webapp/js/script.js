// ── Tab switcher (Sign In / Register) ──
function switchTab(id, btn) {
  document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
  document.querySelectorAll('.form-panel').forEach(p => p.classList.remove('active'));
  btn.classList.add('active');
  document.getElementById(id).classList.add('active');
}

// ── Show any panel by id, hide tabs if needed ──
function showPanel(id) {
  const hideTabs = ['forgot', 'reset'];
  document.querySelectorAll('.form-panel').forEach(p => p.classList.remove('active'));
  document.getElementById(id).classList.add('active');

  const tabs = document.getElementById('main-tabs');
  if (hideTabs.includes(id)) {
    tabs.style.display = 'none';
  } else {
    tabs.style.display = 'flex';
    // Restore correct active tab highlight
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    const match = document.querySelector(`.tab-btn[onclick*="'${id}'"]`);
    if (match) match.classList.add('active');
  }
}

// ── Forgot password link clicked ──
function showForgot(e) {
  e.preventDefault();
  document.getElementById('forgot-success').style.display = 'none';
  showPanel('forgot');
}

// ── Back to Sign In from forgot screen ──
function hideForgot() {
  showPanel('login');
}

// ── Handle forgot password form submit ──
function handleForgotSubmit(e) {
  e.preventDefault();
  const email = document.getElementById('forgot-email').value.trim();
  if (!email) return;

  // Show success message
  const msg = document.getElementById('forgot-success');
  msg.style.display = 'block';

  // Optionally: after 2.5s, redirect to reset panel
  setTimeout(() => {
    showPanel('reset');
  }, 2500);
}