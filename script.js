function calculateDamage() {
    const fields = ['wd', 'sl', 'spl', 'armor', 'protection', 'resistance'];
    for (let id of fields) {
      const val = document.getElementById(id).value;
      if (val === '' || parseFloat(val) < 0 || !Number.isInteger(parseFloat(val))) {
        alert("Please enter a valid non-negative whole number for all inputs.");
        return;
      }
    }
  
    const wd = parseInt(document.getElementById('wd').value);
    const sl = parseInt(document.getElementById('sl').value);
    const c = document.getElementById('crit').checked ? 1 : 0;
    const spl = parseInt(document.getElementById('spl').value);
    const b = document.getElementById('block').checked ? 1 : 0;
    const a = parseInt(document.getElementById('armor').value);
    const p = parseInt(document.getElementById('protection').value);
    const r = parseInt(document.getElementById('resistance').value);
  
    const baseDamage = (1 + 0.5 * c) * (1 + wd + 1.25 * sl) * (1 + 1.3 * spl);
    const reducedDamage = (baseDamage - b * (baseDamage - 1) / 2) *
                          (1 - 0.04 * a) *
                          (1 - 0.04 * p) *
                          (1 - 0.2 * r);
  
    const reductionAmount = baseDamage - reducedDamage;
  
    const result = document.getElementById('result');
    result.innerHTML = `Base Damage: ${baseDamage.toFixed(2)}<br>` +
                       `Damage Reduction: ${reductionAmount.toFixed(2)}<br>` +
                       `Final Damage: ${reducedDamage.toFixed(2)}`;
  }
  